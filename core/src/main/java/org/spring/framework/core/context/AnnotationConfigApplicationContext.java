package org.spring.framework.core.context;

import org.spring.framework.core.bd.BeanDefinitionReader;
import org.spring.framework.core.bd.BeanDefinitionRegistry;
import org.spring.framework.core.bd.RootBeanDefinition;
import org.spring.framework.core.beans.BeanFactory;
import org.spring.framework.core.beans.DefaultListableBeanFactory;
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

    private static final String BEAN_NAME = "applicationContext";

    public AnnotationConfigApplicationContext(Class<?>... configClass) {
        super();
        beanDefinitionReader = new BeanDefinitionReader();
        beanFactory = createBeanFactory();
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

    private BeanFactory createBeanFactory(){
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory(this);
        beanFactory.registerSingleton(BEAN_NAME, this);
        RootBeanDefinition rootBeanDefinition = new RootBeanDefinition();
        rootBeanDefinition.setBeanClass(ApplicationContext.class);
        BeanDefinitionRegistry.put(BEAN_NAME, rootBeanDefinition);
        return beanFactory;
    }

}
