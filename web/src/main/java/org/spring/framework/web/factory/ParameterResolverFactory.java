package org.spring.framework.web.factory;

import org.spring.framework.web.annotation.PathVariable;
import org.spring.framework.web.annotation.RequestBody;
import org.spring.framework.web.annotation.RequestParam;
import org.spring.framework.web.resolver.ParameterResolver;
import org.spring.framework.web.resolver.PathVariableParameterResolver;
import org.spring.framework.web.resolver.RequestBodyParameterResolver;
import org.spring.framework.web.resolver.RequestParamParameterResolver;

import java.lang.reflect.Parameter;

/**
 * @author shuang.kou
 * @createTime 2020年09月28日 10:39:00
 **/
public class ParameterResolverFactory {

    public static ParameterResolver get(Parameter parameter) {
        if (parameter.isAnnotationPresent(RequestParam.class)) {
            return new RequestParamParameterResolver();
        }
        if (parameter.isAnnotationPresent(PathVariable.class)) {
            return new PathVariableParameterResolver();
        }
        if (parameter.isAnnotationPresent(RequestBody.class)) {
            return new RequestBodyParameterResolver();
        }
        return null;
    }
}
