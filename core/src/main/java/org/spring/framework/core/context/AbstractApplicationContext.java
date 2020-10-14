package org.spring.framework.core.context;

import org.spring.framework.core.bean.BeanFactory;

/**
 * @Author kevin xiajun94@FoxMail.com
 * @Description
 * @name AbstractApplicationContext
 * @Date 2020/09/17 9:11
 */
public abstract class AbstractApplicationContext implements ApplicationContext {

    @Override
    public Object getBean(String beanName) {
        return getBeanFactory().getBean(beanName);
    }

    @Override
    public <T> T getBean(Class<T> beanClass) {
        return getBeanFactory().getBean(beanClass);
    }

    @Override
    public void refresh() {
        onRefresh();
    }

    protected void onRefresh() {

    }

    protected abstract BeanFactory getBeanFactory();

    protected abstract void refreshBeanFactory();

}
