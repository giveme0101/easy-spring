package org.spring.framework.core.aop.jdk;

import org.spring.framework.core.aop.Interceptor;
import org.spring.framework.core.aop.MethodInvocation;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public final class JdkInvocationHandler implements InvocationHandler {

    private final Object target;
    private final Interceptor interceptor;

    private JdkInvocationHandler(Object target, Interceptor interceptor) {
        this.target = target;
        this.interceptor = interceptor;
    }

    public static Object wrap(Object target, Interceptor interceptor){
        return wrap(target.getClass().getClassLoader(), target, interceptor);
    }

    public static Object wrap(ClassLoader classLoader, Object target, Interceptor interceptor) {

        Class<?>[] interfaces = target.getClass().getInterfaces();
        if (null == interfaces || interfaces.length < 1){
            return target;
        }

        JdkInvocationHandler jdkInvocationHandler = new JdkInvocationHandler(target, interceptor);
        return Proxy.newProxyInstance(
                classLoader,
                interfaces,
                jdkInvocationHandler
        );
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) {
        MethodInvocation methodInvocation = new MethodInvocation(target, method, args);
        return interceptor.intercept(methodInvocation);
    }

}
