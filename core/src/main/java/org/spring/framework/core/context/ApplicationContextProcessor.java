package org.spring.framework.core.context;

import lombok.extern.slf4j.Slf4j;
import org.spring.framework.core.BeanPostProcessor;
import org.spring.framework.core.aware.ApplicationContextAware;
import org.spring.framework.core.beans.BeanFactory;
import org.spring.framework.core.config.EmbeddedValueResolverAware;
import org.spring.framework.core.config.ResourceManager;
import org.spring.framework.core.config.StringValueResolver;

/**
 * @Author kevin xiajun94@FoxMail.com
 * @Description
 * @name ApplicationContextProcessor
 * @Date 2020/10/23 9:51
 */
@Slf4j
public class ApplicationContextProcessor implements BeanPostProcessor {

    private AnnotationConfigApplicationContext applicationContext;

    public ApplicationContextProcessor(ApplicationContext applicationContext) {
        this.applicationContext = (AnnotationConfigApplicationContext) applicationContext;
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) {

        invokeAwareInterfaces(bean);

        return bean;
    }

    private void invokeAwareInterfaces(Object bean){

        Class beanClass = bean.getClass();

        if (bean instanceof ApplicationContextAware){
            log.debug("ApplicationContextAware: {}", beanClass.getName());
            ((ApplicationContextAware) bean).setApplicationContext(applicationContext);
        }

        if (bean instanceof EmbeddedValueResolverAware){
            log.debug("EmbeddedValueResolverAware: {}", beanClass.getName());
            ((EmbeddedValueResolverAware) bean).setEmbeddedValueResolver(new EmbeddedValueResolver(this.applicationContext.getBeanFactory()));
        }
    }

    private class EmbeddedValueResolver implements StringValueResolver {

        private ResourceManager resourceManager;

        public EmbeddedValueResolver(BeanFactory beanFactory) {
            this.resourceManager = beanFactory.getBean(ResourceManager.class);
        }

        @Override
        public String resolveStringValue(String strVal) {
            return resourceManager.resolveStringValue(strVal);
        }
    }

}
