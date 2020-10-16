package org.spring.framework.core.context;

import org.spring.framework.core.bean.BeanFactory;
import org.spring.framework.core.bean.DefaultListableBeanFactory;
import org.spring.framework.core.bd.BeanDefinitionHolder;
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
        this.beanFactory = new DefaultListableBeanFactory();
        BeanDefinitionHolder.putAll(AnnotationConfigLoader.registerAnnotationConfigClass());
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
