package org.spring.framework.core;

import org.spring.framework.core.beans.PropertyValues;

public interface InstantiationAwareBeanPostProcessor extends BeanPostProcessor{

    Object postProcessBeforeInstantiation(Class<?> beanClass, String beanName);

    boolean postProcessAfterInstantiation(Object bean, String beanName);

    PropertyValues postProcessPropertyValues(PropertyValues pvs, Object bean, String beanName);

}
