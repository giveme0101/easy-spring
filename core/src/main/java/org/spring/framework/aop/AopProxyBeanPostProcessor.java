package org.spring.framework.aop;

import org.spring.framework.core.BeanPostProcessor;

import java.util.List;

/**
 * @Author kevin xiajun94@FoxMail.com
 * @Description
 * @name AopProxyBeanPostProcessor
 * @Date 2020/10/14 15:27
 */
public class AopProxyBeanPostProcessor implements BeanPostProcessor {

    private List<Interceptor> interceptors;

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) {

        if (null == interceptors){
            interceptors = InterceptorFactory.getInterceptors();
        }

        Object wrapperProxyBean = bean;
        for (Interceptor interceptor : interceptors) {

            Class targetClass = interceptor.getTargetClass();

            if (targetClass.isAssignableFrom(wrapperProxyBean.getClass())){

                AdvisedSupport support = new AdvisedSupport();
                support.setTargetClass(targetClass);
                support.setTarget(wrapperProxyBean);
                support.setInterceptor(interceptor);

                AopProxyFactory proxyFactory = new DefaultAopProxyFactory();
                AopProxy aopProxy = proxyFactory.createAopProxy(support);

                wrapperProxyBean = aopProxy.getProxy();
            }
        }

        return wrapperProxyBean;
    }
}
