package org.spring.framework.aop;

import org.spring.framework.aop.cglib.CglibAopProxy;
import org.spring.framework.aop.jdk.JdkAopProxy;

import java.lang.reflect.Proxy;

/**
 * @Author kevin xiajun94@FoxMail.com
 * @Description
 * @name DefaultAopProxyFactory
 * @Date 2020/10/16 9:05
 */
public class DefaultAopProxyFactory implements AopProxyFactory{

    @Override
    public AopProxy createAopProxy(AdvisedSupport config) {

        Class<?> targetClass = config.getTargetClass();
        if (targetClass.isInterface() || Proxy.isProxyClass(targetClass)){
            return new JdkAopProxy(config);
        }

        return new CglibAopProxy(config);
    }

}
