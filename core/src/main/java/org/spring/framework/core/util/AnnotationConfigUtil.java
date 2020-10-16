package org.spring.framework.core.util;

import org.spring.framework.aop.AopProxyBeanPostProcessor;
import org.spring.framework.core.bean.AutowiredAnnotationBeanPostProcessor;
import org.spring.framework.core.bean.BeanDefinitionParser;
import org.spring.framework.core.bean.ValueAnnotationBeanPostProcessor;
import org.spring.framework.core.beandefinition.BeanDefinition;
import org.spring.framework.core.event.EventBeanPostProcessor;
import org.spring.framework.jdbc.ConnectionFactoryBean;
import org.spring.framework.jdbc.JdbcTemplateImpl;

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

        bds.add(BeanDefinitionParser.parse(EventBeanPostProcessor.class));
        bds.add(BeanDefinitionParser.parse(ValueAnnotationBeanPostProcessor.class));
        bds.add(BeanDefinitionParser.parse(AutowiredAnnotationBeanPostProcessor.class));

        bds.add(BeanDefinitionParser.parse(AopProxyBeanPostProcessor.class));

        bds.add(BeanDefinitionParser.parse(ConnectionFactoryBean.class));
        bds.add(BeanDefinitionParser.parse(JdbcTemplateImpl.class));

        return bds;
    }

}
