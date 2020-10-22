package org.spring.framework.core.context;

import org.spring.framework.core.beans.BeanFactory;
import org.spring.framework.core.beans.DefaultListableBeanFactory;
import org.spring.framework.core.bd.BeanDefinitionRegistry;
import org.spring.framework.core.bd.BeanDefinitionReader;
import org.spring.framework.core.config.AnnotationConfigLoader;

/**
 * @Author kevin xiajun94@FoxMail.com
 * @Description
 * @name AnnotationConfigApplicationContext
 * @Date 2020/09/17 9:10
 */
public class AnnotationConfigApplicationContext extends AbstractApplicationContext {

    private BeanDefinitionReader beanDefinitionReader;

    private BeanFactory beanFactory;

    public AnnotationConfigApplicationContext(Class<?>... configClass) {
        super();
        beanDefinitionReader = new BeanDefinitionReader();
        this.beanFactory = new DefaultListableBeanFactory(this);
        BeanDefinitionRegistry.putAll(AnnotationConfigLoader.registerAnnotationConfigClass());
        this.beanDefinitionReader.register(configClass);
        this.refresh();
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
}
