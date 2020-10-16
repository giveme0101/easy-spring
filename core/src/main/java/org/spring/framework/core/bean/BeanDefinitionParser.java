package org.spring.framework.core.bean;

import org.spring.framework.core.annotation.Lazy;
import org.spring.framework.core.annotation.Scope;
import org.spring.framework.core.beandefinition.BeanDefinition;

/**
 * @Author kevin xiajun94@FoxMail.com
 * @Description
 * @name BeanDefinitionParser
 * @Date 2020/10/16 10:48
 */
public class BeanDefinitionParser {

    public static BeanDefinition parse(Class beanClass){

        BeanDefinition bd = new BeanDefinition();

        if (beanClass.isAnnotationPresent(Scope.class)) {
            Scope scope = (Scope) beanClass.getAnnotation(Scope.class);
            bd.setScope(scope.value());
        }

        bd.setBeanClass(beanClass);
        bd.setIsLazyInit(beanClass.isAnnotationPresent(Lazy.class));

        // 是否是工厂bean
        if (FactoryBean.class.isAssignableFrom(beanClass)) {
            bd.setIsFactoryBean(true);
        }

        return bd;
    }

}