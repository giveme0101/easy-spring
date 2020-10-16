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

            // TODO Object的方法不代理

            result = targetMethod.invoke(targetObject, args);
            log.info("invoke target method successfully ,result is: [{}]", result);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e.getCause());
        }

        return result;
    }
}