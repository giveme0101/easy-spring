package org.spring.framework.core.util;

import org.spring.framework.core.beandefinition.BeanDefinition;
import org.spring.framework.core.bean.AutowiredAnnotationBeanPostProcessor;

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

        BeanDefinition bd = new BeanDefinition();
        bd.setBeanClass(AutowiredAnnotationBeanPostProcessor.class);
        bds.add(bd);

        return bds;
    }

}
