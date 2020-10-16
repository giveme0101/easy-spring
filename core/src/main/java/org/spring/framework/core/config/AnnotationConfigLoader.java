package org.spring.framework.core.config;

import org.spring.framework.aop.AopProxyBeanPostProcessor;
import org.spring.framework.core.bd.BeanDefinition;
import org.spring.framework.core.bean.BeanDefinitionParser;
import org.spring.framework.core.event.EventBeanPostProcessor;
import org.spring.framework.ioc.AutowiredAnnotationBeanPostProcessor;
import org.spring.framework.ioc.ValueAnnotationBeanPostProcessor;
import org.spring.framework.jdbc.ConnectionFactoryBean;
import org.spring.framework.jdbc.JdbcTemplateImpl;
import org.spring.framework.jdbc.tm.TransactionManager;
import org.spring.framework.jdbc.tm.TransactionalAnnotationBeanPostProcessor;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * @Author kevin xiajun94@FoxMail.com
 * @Description
 * @name AnnotationConfigLoader
 * @Date 2020/10/13 14:55
 */
public class AnnotationConfigLoader {

    public static Set<BeanDefinition> registerAnnotationConfigClass(){
        return new LinkedHashSet<BeanDefinition>(){{

            add(BeanDefinitionParser.parse(EventBeanPostProcessor.class));
            add(BeanDefinitionParser.parse(ValueAnnotationBeanPostProcessor.class));
            add(BeanDefinitionParser.parse(AutowiredAnnotationBeanPostProcessor.class));

            add(BeanDefinitionParser.parse(AopProxyBeanPostProcessor.class));

            add(BeanDefinitionParser.parse(ConnectionFactoryBean.class));
            add(BeanDefinitionParser.parse(JdbcTemplateImpl.class));
            add(BeanDefinitionParser.parse(TransactionManager.class));
            add(BeanDefinitionParser.parse(TransactionalAnnotationBeanPostProcessor.class));

        }};
    }

}
