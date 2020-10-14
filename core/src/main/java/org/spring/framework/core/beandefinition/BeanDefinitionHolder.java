package org.spring.framework.core.beandefinition;

import org.spring.framework.core.util.BeanUtil;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author kevin xiajun94@FoxMail.com
 * @Description
 * @name BeanDefinitionHolder
 * @Date 2020/09/17 9:30
 */
public class BeanDefinitionHolder {

    private static Map<String, BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<>();
    private static Map<Class, Set<BeanDefinition>> beanClassDefinitionMap = new ConcurrentHashMap<>();

    public static void putAll(Set<BeanDefinition> beanDefinitionSet) {
        beanDefinitionSet.forEach(BeanDefinitionHolder::put);
    }

    public static void put(BeanDefinition beanDefinition){
        put(BeanUtil.getBeanName(beanDefinition), beanDefinition);
    }

    public static void put(String beanName, BeanDefinition beanDefinition){

        beanDefinitionMap.put(beanName, beanDefinition);

        Class<?> beanClass = beanDefinition.getBeanClass();
        beanClassDefinitionMap.computeIfAbsent(beanClass, bd -> new HashSet<>()).add(beanDefinition);
        Class<?>[] interfaces = beanClass.getInterfaces();
        for (final Class<?> anInterface : interfaces) {
            beanClassDefinitionMap.computeIfAbsent(anInterface, bd -> new HashSet<>()).add(beanDefinition);
        }
    }

    public static BeanDefinition get(String beanName){
        return beanDefinitionMap.get(beanName);
    }

    public static BeanDefinition get(Class clazz){
        Set<BeanDefinition> beanDefinitions = beanClassDefinitionMap.get(clazz);
        if (null == beanDefinitions || beanDefinitions.isEmpty()){
            return null;
        }

        if (beanDefinitions.size() != 1){
            throw new RuntimeException("存在多个。。。");
        }

        return beanDefinitions.toArray(new BeanDefinition[0])[0];
    }

    public static Map<String, BeanDefinition> getBeanDefinitionMap() {
        return beanDefinitionMap;
    }
}
