package org.spring.framework.core.aop.cglib;

import org.spring.framework.core.aop.Interceptor;
import org.spring.framework.core.aop.InterceptorFactory;
import org.spring.framework.core.config.BeanPostProcessor;

import java.util.List;

/**
 * @author tom
 * JDK implementation of dynamic proxy
 * @createTime 2020年10月6日10:20:26
 */
public class CglibAopProxyBeanPostProcessor implements BeanPostProcessor {

    private List<Interceptor> interceptors;

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) {

        Object wrapperProxyBean = bean;
        if (null == interceptors){
            interceptors = InterceptorFactory.getInterceptors();
        }
        for (Interceptor interceptor : interceptors) {
            if (interceptor.supports(bean)) {
                wrapperProxyBean = CglibMethodInterceptor.wrap(wrapperProxyBean, interceptor);
            }
        }
        return wrapperProxyBean;
    }

}