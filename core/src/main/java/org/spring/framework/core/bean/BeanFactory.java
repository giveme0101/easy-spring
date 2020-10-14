package org.spring.framework.core.bean;

/**
 * @Author kevin xiajun94@FoxMail.com
 * @Description
 * @name BeanFactory
 * @Date 2020/09/17 13:19
 */
public interface BeanFactory {

    Object getBean(String beanName);

    <T> T getBean(Class<T> beanClass);

    void refresh();

}
