package org.spring.framework.core.util;

import org.spring.framework.core.aop.cglib.CglibAopProxyBeanPostProcessor;
import org.spring.framework.core.aop.jdk.JdkAopProxyBeanPostProcessor;
import org.spring.framework.core.bean.AutowiredAnnotationBeanPostProcessor;
import org.spring.framework.core.bean.ValueAnnotationBeanPostProcessor;
import org.spring.framework.core.beandefinition.BeanDefinition;
import org.spring.framework.core.event.EventBeanPostProcessor;

import java.util.HashSet;
import java.util.Set;

/**
 * @Author kevin xiajun94@FoxMail.com
 * @Description
 * @name AnnotationConfigUtil
 * @Date 2020/10/13 14:55
 */
public class AnnotationConfigUtil {

    public static Set<BeanDefinition> registerAnnotationConfigClass(){

        Set<BeanDefinition> bds = new HashSet<>();

        BeanDefinition autowiredBd = new BeanDefinition();
        autowiredBd.setBeanClass(AutowiredAnnotationBeanPostProcessor.class);
        bds.add(autowiredBd);

        BeanDefinition eventBd = new BeanDefinition();
        eventBd.setBeanClass(EventBeanPostProcessor.class);
        bds.add(eventBd);

        BeanDefinition valueBd = new BeanDefinition();
        valueBd.setBeanClass(ValueAnnotationBeanPostProcessor.class);
        bds.add(valueBd);

        BeanDefinition jdkAopBd = new BeanDefinition();
        jdkAopBd.setBeanClass(JdkAopProxyBeanPostProcessor.class);
        bds.add(jdkAopBd);

        BeanDefinition cglibAopBd = new BeanDefinition();
        cglibAopBd.setBeanClass(CglibAopProxyBeanPostProcessor.class);
        bds.add(cglibAopBd);

        return bds;
    }

}
