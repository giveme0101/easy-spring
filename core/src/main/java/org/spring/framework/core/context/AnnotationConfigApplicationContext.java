package org.spring.framework.core.context;

import org.spring.framework.core.bean.BeanFactory;
import org.spring.framework.core.bean.DefaultListableBeanFactory;
import org.spring.framework.core.beandefinition.BeanDefinitionHolder;
import org.spring.framework.core.beandefinition.BeanDefinitionReader;
import org.spring.framework.core.util.AnnotationConfigUtil;
import org.spring.framework.core.util.ContextLoader;

/**
 * @Author kevin xiajun94@FoxMail.com
 * @Description
 * @name AnnotationConfigApplicationContext
 * @Date 2020/09/17 9:10
 */
public class AnnotationConfigApplicationContext extends AbstractApplicationContext {

    private BeanDefinitionReader beanDefinitionReader;

    private BeanFactory beanFactory;

    private AnnotationConfigApplicationContext() {
        beanDefinitionReader = new BeanDefinitionReader();
        this.beanFactory = new DefaultListableBeanFactory();
    }

    public AnnotationConfigApplicationContext(Class<?>... configClass) {
        this();
        ContextLoader.put(this);
        BeanDefinitionHolder.putAll(AnnotationConfigUtil.registerAnnotationConfigClass());
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
