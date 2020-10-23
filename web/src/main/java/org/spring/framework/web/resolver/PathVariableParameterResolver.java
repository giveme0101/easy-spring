package org.spring.framework.web.resolver;


import org.spring.framework.web.annotation.PathVariable;
import org.spring.framework.web.entity.MethodDetail;
import org.spring.framework.web.util.ObjectUtil;

import java.lang.reflect.Parameter;
import java.util.Map;

/**
 * process @PathVariable annotation
 *
 * @author shuang.kou
 * @createTime 2020年09月27日 20:58:00
 **/
public class PathVariableParameterResolver implements ParameterResolver {

    @Override
    public Object resolve(MethodDetail methodDetail, Parameter parameter) {
        PathVariable pathVariable = parameter.getDeclaredAnnotation(PathVariable.class);
        String requestParameter = pathVariable.value();
        Map<String, String> urlParameterMappings = methodDetail.getUrlParameterMappings();
        String requestParameterValue = urlParameterMappings.get(requestParameter);
        return ObjectUtil.convert(parameter.getType(), requestParameterValue);
    }

}
