package org.spring.framework.core.beandefinition;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author kevin xiajun94@FoxMail.com
 * @Description
 * @name BeanDefinitionHolder
 * @Date 2020/09/17 9:30
 */
public class BeanDefinitionHolder {

    private static Map<String, BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<>();

    public static void put(String beanName, BeanDefinition beanDefinition){
        beanDefinitionMap.put(beanName, beanDefinition);
    }

    public static BeanDefinition get(String beanName){
        return beanDefinitionMap.get(beanName);
    }

    public static Map<String, BeanDefinition> getBeanDefinitionMap() {
        return beanDefinitionMap;
    }
}
