package org.spring.framework.core.util;

import org.spring.framework.core.bean.AutowiredAnnotationBeanPostProcessor;
import org.spring.framework.core.bean.ValueAnnotationBeanPostProcessor;
import org.spring.framework.core.beandefinition.BeanDefinition;

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

        BeanDefinition valueBd = new BeanDefinition();
        valueBd.setBeanClass(ValueAnnotationBeanPostProcessor.class);
        bds.add(valueBd);

        return bds;
    }

}
