package org.spring.framework.core.beandefinition;

import lombok.extern.slf4j.Slf4j;
import org.spring.framework.core.util.BeanNameUtil;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author kevin xiajun94@FoxMail.com
 * @Description
 * @name BeanDefinitionHolder
 * @Date 2020/09/17 9:30
 */
@Slf4j
public class BeanDefinitionHolder {

    private static Map<String, BeanDefinition> beanDefinitionMap = new LinkedHashMap<>();
    private static Map<Class, Set<BeanDefinition>> beanClassDefinitionMap = new ConcurrentHashMap<>();

    public static void putAll(Set<BeanDefinition> beanDefinitionSet) {
        beanDefinitionSet.forEach(BeanDefinitionHolder::put);
    }

    public static void put(BeanDefinition beanDefinition){
        put(BeanNameUtil.getBeanName(beanDefinition), beanDefinition);
    }

    public static void put(String beanName, BeanDefinition beanDefinition){

        beanDefinitionMap.put(beanName, beanDefinition);
        log.debug("register bean: {}", beanName);

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
        Collection<BeanDefinition> beanDefinitions = getBeansOfType(clazz);
        if (null == beanDefinitions || beanDefinitions.isEmpty()){
            return null;
        }

        if (beanDefinitions.size() != 1){
            throw new RuntimeException("存在多个。。。");
        }

        return beanDefinitions.toArray(new BeanDefinition[0])[0];
    }

    public static Collection<BeanDefinition> getBeansOfType(Class clazz){
       return beanClassDefinitionMap.get(clazz);
    }

    public static Map<String, BeanDefinition> getBeanDefinitionMap() {
        return new LinkedHashMap<>(beanDefinitionMap);
    }
}
