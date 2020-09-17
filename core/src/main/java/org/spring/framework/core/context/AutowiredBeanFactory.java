package org.spring.framework.core.context;

import lombok.extern.slf4j.Slf4j;
import org.spring.framework.core.Autowired;
import org.spring.framework.core.aware.BeanNameAware;
import org.spring.framework.core.beandefinition.BeanDefinition;
import org.spring.framework.core.beandefinition.BeanDefinitionHolder;
import org.spring.framework.core.config.BeanPostProcessor;
import org.spring.framework.core.config.InitializingBean;
import org.spring.framework.core.factorybean.FactoryBean;
import org.spring.framework.core.util.EscapeUtil;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author kevin xiajun94@FoxMail.com
 * @Description
 * @name AutowiredBeanFactory
 * @Date 2020/09/17 13:29
 */
@Slf4j
public class AutowiredBeanFactory implements BeanFactory {

    private Map<String, Object> singletonObjects = new ConcurrentHashMap<>();

    @Override
    public Object getBean(String beanName) {
        BeanDefinition beanDefinition = BeanDefinitionHolder.get(beanName);
        if (null == beanDefinition){
            return null;
        }

        if (beanDefinition.getIsPrototype()) {
            return doCreateBean(beanName, beanDefinition);
        }

        if (beanDefinition.getIsLazyInit()) {
            Object bean = doCreateBean(beanName, beanDefinition);
            singletonObjects.put(beanName, bean);
            return bean;
        }

        if (beanDefinition.getIsFactoryBean()){
            Object bean = singletonObjects.get(beanName);
            bean = ((FactoryBean) bean).getObject();
            return bean;
        }

        return singletonObjects.get(beanName);
    }

    @Override
    public <T> T getBean(Class<T> beanClass) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void refresh() {
        Map<String, BeanDefinition> beanDefinitionMap = BeanDefinitionHolder.getBeanDefinitionMap();
        for (final Map.Entry<String, BeanDefinition> entry : beanDefinitionMap.entrySet()) {

            String beanName = entry.getKey();

            BeanDefinition bd = entry.getValue();

            if (bd.getIsPrototype() || bd.getIsLazyInit()){
                continue;
            }

            Object bean = doCreateBean(beanName, bd);
            singletonObjects.put(beanName, bean);
        }
    }

    private Object doCreateBean(String beanName, BeanDefinition bd) {

        Class<?> beanClass = bd.getBeanClass();

        // 1. 实例化
        Object bean = doInstance(beanClass);

        // 2. 设置属性
        populateBean(bean, beanClass);

        // aware
        doAware(bean, beanName, beanClass);

        // BeanPostProcessor beanPostBeforeInitialization
        bean = beanPostBeforeInitialization(bean, beanName);

        // 3. 初始化
        // InitializationBean -> afterPropertySet
        init(bean);

        // BeanPostProcessor beanPostAfterInitialization
        bean = beanPostAfterInitialization(bean, beanName);

        return bean;
    }

    private Object doInstance(Class<?> beanClass){
        // TODO 构造器注入
        try {
            Constructor<?> constructor = beanClass.getConstructor();
            return constructor.newInstance();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException invocationTargetException) {
            invocationTargetException.printStackTrace();
        }
        return null;
    }

    private void populateBean(Object bean, Class<?> beanClass) {

        // 字段注入
        Field[] declaredFields = beanClass.getDeclaredFields();
        for (final Field field : declaredFields) {
            if (field.isAnnotationPresent(Autowired.class)){
                try {
                    field.setAccessible(true);
                    String requiredBeanName = field.getName();
                    field.set(bean, getBean(requiredBeanName));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }

        // Setter注入
        Method[] declaredMethods = beanClass.getDeclaredMethods();
        for (final Method method : declaredMethods) {
            if (method.isAnnotationPresent(Autowired.class)){
                try {
                    String setterName = method.getName();
                    String beanName = transSetterNameToBeanName(setterName);
                    method.setAccessible(true);
                    method.invoke(bean, getBean(beanName));
                } catch (Exception ex){
                    ex.printStackTrace();
                }
            }
        }
    }

    private String transSetterNameToBeanName(String setterName) {
        String beanName = setterName.substring(3);
        beanName = EscapeUtil.firstCharLowerCase(beanName);
        return beanName;
    }

    private void doAware(Object bean, String beanName, Class<?> beanClass) {

        if (bean instanceof BeanNameAware){
            log.debug("invoke BeanNameAware: {}", beanClass.getName());
            ((BeanNameAware) bean).setBeanName(beanName);
        }
    }

    private void init(Object bean) {
        if (bean instanceof InitializingBean){
            log.debug("invoke InitializingBean.afterPropertiesSet(): {}", bean);
            ((InitializingBean) bean).afterPropertiesSet();
        }
    }

    private Object beanPostAfterInitialization(Object bean, String beanName) {

        Object postBean = bean;
        for (final Object object : singletonObjects.values()) {
            if (object instanceof BeanPostProcessor){
                BeanPostProcessor postProcessor = (BeanPostProcessor) object;
                postBean = postProcessor.postProcessBeforeInitialization(bean, beanName);
            }
        }

        return postBean;
    }

    private Object beanPostBeforeInitialization(Object bean, String beanName) {

        Object postBean = bean;
        for (final Object object : singletonObjects.values()) {
            if (object instanceof BeanPostProcessor){
                BeanPostProcessor postProcessor = (BeanPostProcessor) object;
                postBean = postProcessor.postProcessAfterInitialization(bean, beanName);
            }
        }

        return postBean;
    }
}
