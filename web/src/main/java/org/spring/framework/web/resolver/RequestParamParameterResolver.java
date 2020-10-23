package org.spring.framework.web.resolver;


import org.spring.framework.web.annotation.RequestParam;
import org.spring.framework.web.entity.MethodDetail;
import org.spring.framework.web.util.ObjectUtil;

import java.lang.reflect.Parameter;

/**
 * process @RequestParam annotation
 *
 * @author shuang.kou
 * @createTime 2020年09月27日 20:58:00
 **/
public class RequestParamParameterResolver implements ParameterResolver {

    @Override
    public Object resolve(MethodDetail methodDetail, Parameter parameter) {
        RequestParam requestParam = parameter.getDeclaredAnnotation(RequestParam.class);
        String requestParameter = requestParam.value();
        String requestParameterValue = methodDetail.getQueryParameterMappings().get(requestParameter);
        if (requestParameterValue == null) {
            throw new IllegalArgumentException("The specified parameter " + requestParameter + " can not be null!");
        }
        // convert the parameter to the specified type
        return ObjectUtil.convert(parameter.getType(), requestParameterValue);

    }
}
