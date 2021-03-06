package org.spring.framework.aop;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author tom
 * @createTime 2020年10月6日10:20:26
 */
@Slf4j
@Getter
@AllArgsConstructor
public class MethodInvocation {

    private final Object targetObject;
    private final Method targetMethod;
    private final Object[] args;

    public Object proceed() {
        Object result;
        try {

            String methodName = targetMethod.getName();

            if ("toString".equals(methodName)){
                return targetObject.toString();
            }

            result = targetMethod.invoke(targetObject, args);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e.getCause());
        }

        return result;
    }
}
