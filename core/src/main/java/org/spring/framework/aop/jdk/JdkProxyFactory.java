package org.spring.framework.aop.jdk;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

/**
 * @Author kevin xiajun94@FoxMail.com
 * @Description
 * @name JdkProxyFactory
 * @Date 2020/10/16 14:42
 */
public class JdkProxyFactory {
    public static Object createProxy(ClassLoader classLoader, Class[] targetInterfaces, InvocationHandler jdkInvocationHandler){
        return Proxy.newProxyInstance(classLoader, targetInterfaces, (target, method, args) -> {

            if ("toString".equals(method.getName())){
                return target.toString();
            }

            return jdkInvocationHandler.invoke(target, method, args);
        });
    }

}
