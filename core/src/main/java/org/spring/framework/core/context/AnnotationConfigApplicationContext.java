package org.spring.framework.core.context;

import org.spring.framework.core.beandefinition.BeanDefinitionReader;

/**
 * @Author kevin xiajun94@FoxMail.com
 * @Description
 * @name AnnotationConfigApplicationContext
 * @Date 2020/09/17 9:10
 */
public class AnnotationConfigApplicationContext extends AbstractApplicationContext {

    private BeanDefinitionReader beanDefinitionReader;

    private BeanFactory beanFactory;

    public AnnotationConfigApplicationContext() {
        beanDefinitionReader = new BeanDefinitionReader();
        this.beanFactory = new AutowiredBeanFactory();
    }

    public AnnotationConfigApplicationContext(Class<?>... configClass) {
        this();
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
