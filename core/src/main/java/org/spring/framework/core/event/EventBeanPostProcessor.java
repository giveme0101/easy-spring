package org.spring.framework.core.event;

import org.spring.framework.core.config.BeanPostProcessor;
import org.spring.framework.core.util.ContextLoader;

/**
 * @Author kevin xiajun94@FoxMail.com
 * @Description
 * @name EventBeanPostProcessor
 * @Date 2020/10/15 11:32
 */
public class EventBeanPostProcessor implements BeanPostProcessor {

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) {

        if (bean instanceof EventListener){
            ApplicationEventPublish context = (ApplicationEventPublish) ContextLoader.getContext();
            context.addListener((EventListener) bean);
        }

        return bean;
    }

}
