package org.spring.framework.core.bd;

import lombok.extern.slf4j.Slf4j;
import org.spring.framework.core.exception.NoSuchBeanDefinitionException;
import org.spring.framework.core.util.BeanNameUtil;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * @Author kevin xiajun94@FoxMail.com
 * @Description
 * @name BeanDefinitionRegistry
 * @Date 2020/09/17 9:30
 */
@Slf4j
public class BeanDefinitionRegistry {

    private static Map<String, RootBeanDefinition> beanDefinitionMap = new LinkedHashMap<>();
    private static final Map<Class, Set<String>> allBeanNamesByType = new ConcurrentHashMap<>(64);

    public static void putAll(Set<RootBeanDefinition> beanDefinitionSet) {
        beanDefinitionSet.forEach(BeanDefinitionRegistry::put);
    }

    public static void put(RootBeanDefinition beanDefinition){
        put(BeanNameUtil.getBeanName(beanDefinition), beanDefinition);
    }

    public static void put(String beanName, RootBeanDefinition beanDefinition){

        beanDefinitionMap.put(beanName, beanDefinition);
        log.debug("register bean: {}", beanName);

        Class<?> beanClass = beanDefinition.getBeanClass();
        allBeanNamesByType.computeIfAbsent(beanClass, bd -> new HashSet<>()).add(beanName);
        Class<?>[] interfaces = beanClass.getInterfaces();
        for (final Class<?> anInterface : interfaces) {
            allBeanNamesByType.computeIfAbsent(anInterface, bd -> new HashSet<>()).add(beanName);
        }
    }

    public static RootBeanDefinition get(String beanName){
        return beanDefinitionMap.get(beanName);
    }

    public static RootBeanDefinition get(Class clazz){
        Collection<RootBeanDefinition> beanDefinitions = getBeansOfType(clazz);
        if (null == beanDefinitions || beanDefinitions.isEmpty()){
            return null;
        }

        if (beanDefinitions.size() != 1){
            throw new NoSuchBeanDefinitionException("存在多个beanDefinition");
        }

        return beanDefinitions.toArray(new RootBeanDefinition[0])[0];
    }

    public static Collection<RootBeanDefinition> getBeansOfType(Class clazz){
        Set<String> beanNameOfTypes = allBeanNamesByType.get(clazz);
        return beanNameOfTypes.stream().map(beanDefinitionMap::get).collect(Collectors.toList());
    }

    public static Map<String, RootBeanDefinition> getBeanDefinitionMap() {
        return new LinkedHashMap<>(beanDefinitionMap);
    }

}
