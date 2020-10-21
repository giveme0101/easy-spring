package org.spring.framework.core.bean;

import java.util.Map;

/**
 * @Author kevin xiajun94@FoxMail.com
 * @Description
 * @name BeanFactory
 * @Date 2020/09/17 13:19
 */
public interface BeanFactory {

    String FACTORY_BEAN_PREFIX = "&";

    Object getBean(String beanName);

    <T> T getBean(Class<T> beanClass);

    <T> Map<String, T> getBeansOfType(Class<T> beanClass);

    void refresh();

}
