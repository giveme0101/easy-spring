package org.spring.framework.core.event;

import org.spring.framework.core.BeanPostProcessor;
import org.spring.framework.core.aware.ApplicationContextAware;
import org.spring.framework.core.context.ApplicationContext;

/**
 * @Author kevin xiajun94@FoxMail.com
 * @Description
 * @name EventBeanPostProcessor
 * @Date 2020/10/15 11:32
 */
public class EventBeanPostProcessor implements BeanPostProcessor, ApplicationContextAware {

    private ApplicationContext applicationContext;

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) {

        if (bean instanceof EventListener){
            applicationContext.addListener((EventListener) bean);
        }

        return bean;
    }

    @Override
    public void setApplicationContext(ApplicationContext context) {
        this.applicationContext = context;
    }
}
