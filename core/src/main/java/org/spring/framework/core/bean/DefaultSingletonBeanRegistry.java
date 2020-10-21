package org.spring.framework.core.bean;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author kevin xiajun94@FoxMail.com
 * @Description
 * @name DefaultSingletonBeanRegistry
 * @Date 2020/10/20 17:42
 */
public class DefaultSingletonBeanRegistry implements SingletonBeanRegistry {

    private Map<String, Object> singletonObjects = new ConcurrentHashMap<>();

    @Override
    public void registerSingleton(String beanName, Object singletonObject) {
        singletonObjects.put(beanName, singletonObject);
    }

    @Override
    public Object getSingleton(String beanName) {
        return singletonObjects.get(beanName);
    }

    @Override
    public boolean containsSingleton(String beanName) {
        return singletonObjects.containsKey(beanName);
    }

    @Override
    public String[] getSingletonNames() {
        return singletonObjects.keySet().toArray(new String[0]);
    }

    @Override
    public int getSingletonCount() {
        return singletonObjects.size();
    }
}
