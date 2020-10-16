package org.spring.framework.aop.cglib;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import org.spring.framework.aop.Interceptor;
import org.spring.framework.aop.MethodInvocation;
import org.spring.framework.core.context.ApplicationContext;
import org.spring.framework.core.util.ContextLoader;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * @author shuang.kou
 * @createTime 2020年10月09日 21:43:00
 **/
public class CglibMethodInterceptor implements MethodInterceptor {

    private final Object target;
    private final Interceptor interceptor;
    private static ApplicationContext applicationContext;

    public CglibMethodInterceptor(Object target, Interceptor interceptor) {
        this.target = target;
        this.interceptor = interceptor;
    }

    public static Object wrap(Object target, Interceptor interceptor){
        return wrap(target.getClass().getClassLoader(), target, interceptor);
    }

    public static Object wrap(ClassLoader classLoader, Object target, Interceptor interceptor) {

        Class<?> targetClass = target.getClass();

        if (null == applicationContext){
            applicationContext = ContextLoader.getContext();
        }

        Enhancer enhancer = new Enhancer();
        enhancer.setClassLoader(classLoader);
        enhancer.setSuperclass(targetClass);
        enhancer.setCallback(new CglibMethodInterceptor(target, interceptor));

        Constructor<?>[] declaredConstructors = targetClass.getDeclaredConstructors();
        for (final Constructor<?> constructor : declaredConstructors) {
            Class<?>[] parameterTypes = constructor.getParameterTypes();
            try {
                List param = new ArrayList(parameterTypes.length);
                for (final Class<?> type : parameterTypes) {
                    param.add(applicationContext.getBean(type));
                }

                return enhancer.create(parameterTypes, param.toArray());
            } catch (Exception ex){}
        }

        throw new RuntimeException("构造方法注入失败！");
    }

    @Override
    public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) {
        MethodInvocation methodInvocation = new MethodInvocation(target, method, args);
        // the return value is still the result of the proxy class execution
        return interceptor.intercept(methodInvocation);
    }
}
