package org.spring.framework.web.handler;

import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.QueryStringDecoder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.CharEncoding;
import org.apache.commons.codec.Charsets;
import org.spring.framework.core.aware.BeanFactoryAware;
import org.spring.framework.core.beans.BeanFactory;
import org.spring.framework.core.util.BeanNameUtil;
import org.spring.framework.web.entity.MethodDetail;
import org.spring.framework.web.exception.UrlNotFoundException;
import org.spring.framework.web.factory.FullHttpResponseFactory;
import org.spring.framework.web.factory.ParameterResolverFactory;
import org.spring.framework.web.factory.UrlMethodRouter;
import org.spring.framework.web.resolver.ParameterResolver;
import org.spring.framework.web.util.UrlUtil;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Handle get request
 *
 * @author shuang.kou
 * @createTime 2020年09月24日 13:33:00
 **/
@Slf4j
@RequiredArgsConstructor
public class GetRequestHandler implements RequestHandler, BeanFactoryAware {

    private BeanFactory beanFactory;
    private final UrlMethodRouter urlMethodRouter;

    @Override
    public FullHttpResponse handle(FullHttpRequest fullHttpRequest) {

        String requestUri = fullHttpRequest.uri();
        Map<String, String> queryParameterMappings = getQueryParams(requestUri);
        // get http request path，such as "/user"
        String requestPath = UrlUtil.getRequestPath(requestUri);
        // get target method
        MethodDetail methodDetail = urlMethodRouter.getMethod(requestPath, HttpMethod.GET);
        if (methodDetail == null) {
            throw new UrlNotFoundException("url not found");
        }

        methodDetail.setQueryParameterMappings(queryParameterMappings);
        Method targetMethod = methodDetail.getMethod();
        if (targetMethod == null) {
            throw new UrlNotFoundException("url not found");
        }

        log.debug("requestPath -> target method [{}]", targetMethod.getName());
        Parameter[] targetMethodParameters = targetMethod.getParameters();

        // target method parameters.
        // notice! you should convert it to array when pass into the executeMethod method
        List<Object> targetMethodParams = new ArrayList<>();
        for (Parameter parameter : targetMethodParameters) {
            ParameterResolver parameterResolver = ParameterResolverFactory.get(parameter);
            if (parameterResolver != null) {
                Object param = parameterResolver.resolve(methodDetail, parameter);
                targetMethodParams.add(param);
            }
        }

        String beanName = BeanNameUtil.getBeanName(methodDetail.getMethod().getDeclaringClass());
        Object targetObject = beanFactory.getBean(beanName);

        return FullHttpResponseFactory.getSuccessResponse(targetMethod, targetMethodParams, targetObject);
    }

    /**
     * get the parameters of uri
     */
    private Map<String, String> getQueryParams(String uri) {
        QueryStringDecoder queryDecoder = new QueryStringDecoder(uri, Charsets.toCharset(CharEncoding.UTF_8));
        Map<String, List<String>> parameters = queryDecoder.parameters();
        Map<String, String> queryParams = new HashMap<>();
        for (Map.Entry<String, List<String>> attr : parameters.entrySet()) {
            for (String attrVal : attr.getValue()) {
                queryParams.put(attr.getKey(), attrVal);
            }
        }
        return queryParams;
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

}
