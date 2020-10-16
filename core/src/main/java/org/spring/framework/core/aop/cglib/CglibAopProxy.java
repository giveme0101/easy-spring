package org.spring.framework.core.aop.cglib;

import org.spring.framework.core.aop.AdvisedSupport;
import org.spring.framework.core.aop.AopProxy;

/**
 * @Author kevin xiajun94@FoxMail.com
 * @Description
 * @name CglibAopProxy
 * @Date 2020/10/16 8:57
 */
public class CglibAopProxy implements AopProxy {

    private AdvisedSupport support;

    public CglibAopProxy(AdvisedSupport support) {
        this.support = support;
    }

    @Override
    public Object getProxy() {
        return CglibMethodInterceptor.wrap(support.getTarget(), support.getInterceptor());
    }

    @Override
    public Object getProxy(ClassLoader classLoader) {
        return CglibMethodInterceptor.wrap(classLoader, support.getTarget(), support.getInterceptor());
    }

}
