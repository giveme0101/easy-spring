package org.spring.framework.core.aop;

/**
 * @Author kevin xiajun94@FoxMail.com
 * @Description
 * @name AopProxyFactory
 * @Date 2020/10/16 9:02
 */
public interface AopProxyFactory {

    AopProxy createAopProxy(AdvisedSupport config);

}
