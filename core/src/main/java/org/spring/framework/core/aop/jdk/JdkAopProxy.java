package org.spring.framework.core.aop.jdk;

import org.spring.framework.core.aop.AdvisedSupport;
import org.spring.framework.core.aop.AopProxy;

/**
 * @Author kevin xiajun94@FoxMail.com
 * @Description
 * @name JdkAopProxy
 * @Date 2020/10/16 9:08
 */
public class JdkAopProxy implements AopProxy {

    private final AdvisedSupport support;

    public JdkAopProxy(AdvisedSupport support) {
        this.support = support;
    }

    @Override
    public Object getProxy() {
        return JdkInvocationHandler.wrap(support.getTarget(), support.getTargetClass(), support.getInterceptor());
    }

    @Override
    public Object getProxy(ClassLoader classLoader) {
        return JdkInvocationHandler.wrap(classLoader, support.getTarget(), support.getTargetClass(), support.getInterceptor());
    }
}
