package org.spring.framework.core.factorybean;

/**
 * @Author kevin xiajun94@FoxMail.com
 * @Description
 * @name FactoryBean
 * @Date 2020/09/17 14:46
 */
public interface FactoryBean<T> {

    String BEAN_NAME_PREFIX = "&";

    T getObject();

    Class<?> getObjectType();

    default boolean isSingleton() {
        return true;
    }

}
