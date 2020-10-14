package org.spring.framework.core.aware;

import org.spring.framework.core.bean.BeanFactory;

/**
 * @Author kevin xiajun94@FoxMail.com
 * @Description
 *
 * TODO
 *
 * @name BeanFactoryAware
 * @Date 2020/10/13 14:10
 */
public interface BeanFactoryAware {

    void setBeanFactory(BeanFactory beanFactory);

}
