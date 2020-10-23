package org.spring.framework.core.context;

import org.spring.framework.core.bd.BeanDefinitionReader;
import org.spring.framework.core.bd.BeanDefinitionRegistry;
import org.spring.framework.core.beans.BeanFactory;
import org.spring.framework.core.config.AnnotationConfigLoader;

/**
 * @Author kevin xiajun94@FoxMail.com
 * @Description
 * @name AnnotationConfigApplicationContext
 * @Date 2020/09/17 9:10
 */
public class AnnotationConfigApplicationContext extends AbstractApplicationContext {

    private BeanFactory beanFactory;

    private BeanDefinitionReader beanDefinitionReader;

    private Class<?>[] configClass;

    public AnnotationConfigApplicationContext() {
        super();
        beanDefinitionReader = new BeanDefinitionReader();
        beanFactory = createBeanFactory();
        BeanDefinitionRegistry.putAll(AnnotationConfigLoader.registerAnnotationConfigClass());
    }

    @Override
    public ApplicationContext run(Class<?>... configClass) {
        this.configClass = configClass;
        this.beanDefinitionReader.register(configClass);
        this.refresh();
        return this;
    }

    @Override
    protected void onRefresh() {
        refreshBeanFactory();
    }

    @Override
    protected BeanFactory getBeanFactory() {
        return beanFactory;
    }

    @Override
    protected void refreshBeanFactory() {
        beanFactory.refresh();
    }

    @Override
    public void close() {

    }

    public Class<?>[] getConfigClass() {
        return configClass;
    }
}
