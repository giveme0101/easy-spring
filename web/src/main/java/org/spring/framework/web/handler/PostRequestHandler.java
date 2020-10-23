package org.spring.framework.web.handler;

import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpMethod;
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
import java.util.List;

/**
 * @author shuang.kou
 * @createTime 2020年09月24日 13:33:00
 **/
@Slf4j
@RequiredArgsConstructor
public class PostRequestHandler implements RequestHandler, BeanFactoryAware {

    private BeanFactory beanFactory;
    private final UrlMethodRouter urlMethodRouter;

    @Override
    public FullHttpResponse handle(FullHttpRequest fullHttpRequest) {
        String requestUri = fullHttpRequest.uri();
        // get http request path，such as "/user"
        String requestPath = UrlUtil.getRequestPath(requestUri);

        // get target method
        MethodDetail methodDetail = urlMethodRouter.getMethod(requestPath, HttpMethod.POST);
        if (methodDetail == null) {
            throw new UrlNotFoundException("url not found");
        }

        Method targetMethod = methodDetail.getMethod();
        String contentType = this.getContentType(fullHttpRequest.headers());
        // target method parameters.
        // notice! you should convert it to array when pass into the executeMethod()
        List<Object> targetMethodParams = new ArrayList<>();
        if (contentType.equals("application/json")) {
            String json = fullHttpRequest.content().toString(Charsets.toCharset(CharEncoding.UTF_8));
            methodDetail.setJson(json);
            Parameter[] targetMethodParameters = targetMethod.getParameters();
            for (Parameter parameter : targetMethodParameters) {
                ParameterResolver parameterResolver = ParameterResolverFactory.get(parameter);
                if (parameterResolver != null) {
                    Object param = parameterResolver.resolve(methodDetail, parameter);
                    targetMethodParams.add(param);
                }
            }
        } else {
            throw new IllegalArgumentException("only receive application/json type data");
        }
        String beanName = BeanNameUtil.getBeanName(methodDetail.getMethod().getDeclaringClass());
        Object targetObject = beanFactory.getBean(beanName);
        return FullHttpResponseFactory.getSuccessResponse(targetMethod, targetMethodParams, targetObject);
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    private String getContentType(HttpHeaders headers) {
        String typeStr = headers.get("Content-Type");
        String[] list = typeStr.split(";");
        return list[0];
    }

}


