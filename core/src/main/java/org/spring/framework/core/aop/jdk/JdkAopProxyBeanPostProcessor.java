package org.spring.framework.core.aop.jdk;

import org.spring.framework.core.aop.Interceptor;
import org.spring.framework.core.aop.InterceptorFactory;
import org.spring.framework.core.config.BeanPostProcessor;

import java.util.List;

/**
 * @Author kevin xiajun94@FoxMail.com
 * @Description
 * @name JdkAopProxyBeanPostProcessor
 * @Date 2020/10/14 15:27
 */
public class JdkAopProxyBeanPostProcessor implements BeanPostProcessor {

    private List<Interceptor> interceptors;

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) {

        Object wrapperProxyBean = bean;
        if (null == interceptors){
            interceptors = InterceptorFactory.getInterceptors();
        }
        for (Interceptor interceptor : interceptors) {
            if (interceptor.supports(bean)) {
                wrapperProxyBean = JdkInvocationHandler.wrap(wrapperProxyBean, interceptor);
            }
        }

        return wrapperProxyBean;
    }
}
