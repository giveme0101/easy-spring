package org.spring.framework.core.bean;

import lombok.extern.slf4j.Slf4j;
import org.spring.framework.core.aware.BeanNameAware;
import org.spring.framework.core.beandefinition.BeanDefinition;
import org.spring.framework.core.beandefinition.BeanDefinitionHolder;
import org.spring.framework.core.config.BeanPostProcessor;
import org.spring.framework.core.config.InitializingBean;
import org.spring.framework.core.config.InstantiationAwareBeanPostProcessor;
import org.spring.framework.core.util.BeanUtil;

import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author kevin xiajun94@FoxMail.com
 * @Description
 * @name DefaultListableBeanFactory
 * @Date 2020/09/17 13:29
 */
@Slf4j
public class DefaultListableBeanFactory implements BeanFactory {

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

        Object bean = singletonObjects.get(beanName);
        if (null == bean){
           return doCreateBean(beanName, beanDefinition);
        }

        return bean;
    }

    @Override
    public <T> T getBean(Class<T> beanClass) {

        BeanDefinition beanDefinition = BeanDefinitionHolder.get(beanClass);
        if (null == beanDefinition){
            return null;
        }

        String beanName = BeanUtil.getBeanName(beanDefinition);
        return (T) getBean(beanName);
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

            if (singletonObjects.containsKey(beanName)){
                continue;
            }

            Object bean = doCreateBean(beanName, bd);
            singletonObjects.put(beanName, bean);
        }
    }

    private Object doCreateBean(String beanName, BeanDefinition bd) {

        if (bd.getIsFactoryBean()){

            Class<?> factoryBeanClass = bd.getBeanClass();
            Type type = Arrays.stream(factoryBeanClass.getGenericInterfaces()).filter(i -> FactoryBean.class.isAssignableFrom(factoryBeanClass))
                    .findFirst().get();
            Class productBeanClass = (Class)((ParameterizedType) type).getActualTypeArguments()[0];
            String productBeanName = BeanUtil.getBeanName(bd);

            // 创建工厂bean
            String factoryBeanName = FactoryBean.BEAN_NAME_PREFIX + productBeanName;
            FactoryBean factoryBean = (FactoryBean) createBean(factoryBeanName, bd);
            BeanDefinitionHolder.put(factoryBeanName, bd);

            // 创建工厂bean生产的bean
            BeanDefinition beanDefinition = new BeanDefinition();
            beanDefinition.setBeanClass(productBeanClass);
            beanDefinition.setIsSingleton(factoryBean.isSingleton());
            BeanDefinitionHolder.put(productBeanName, beanDefinition);
            if (factoryBean.isSingleton()){
                singletonObjects.put(productBeanName, factoryBean.getObject());
            }

            return factoryBean;
        } else {
            return createBean(beanName, bd);
        }
    }

    private Object createBean(String beanName, BeanDefinition bd) {

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

        if (InstantiationAwareBeanPostProcessor.class.isAssignableFrom(beanClass)) {
            // TODO 调用前置处理器 return postProcessBeforeInstantiation();
        }

        Object instance = null;

        try {
            Constructor<?>[] constructors = beanClass.getConstructors();
            for (final Constructor<?> constructor : constructors) {
                Parameter[] parameters = constructor.getParameters();
                if (parameters.length == 0){
                    instance = constructor.newInstance();
                    break;
                }

                List<Object> params = new ArrayList<>(parameters.length);
                for (final Parameter parameter : parameters) {

                    Class<?> type = parameter.getType();
                    Object bean = getBean(type);
                    if (null == bean){
                        throw new RuntimeException("无法注入" + type);
                    }

                    params.add(bean);
                }

                instance = constructor.newInstance(params.toArray());
            }

            if (InstantiationAwareBeanPostProcessor.class.isAssignableFrom(beanClass)) {
                // TODO 调用后置处理器 return postProcessAfterInstantiation();
            }

            return instance;
        } catch (IllegalAccessException | InstantiationException | InvocationTargetException e) {
            e.printStackTrace();
        }

        return null;
    }

    private void populateBean(Object bean, Class<?> beanClass) {
        InstantiationAwareBeanPostProcessor autowiredAnnotationBeanPostProcessor = (AutowiredAnnotationBeanPostProcessor) singletonObjects.get("autowiredAnnotationBeanPostProcessor");
        if (null != autowiredAnnotationBeanPostProcessor) {
            BeanDefinition beanDefinition = BeanDefinitionHolder.get(beanClass);
            autowiredAnnotationBeanPostProcessor.postProcessProperties(bean, BeanUtil.getBeanName(beanDefinition));
        }

        InstantiationAwareBeanPostProcessor valueAnnotationBeanPostProcessor = (ValueAnnotationBeanPostProcessor) singletonObjects.get("valueAnnotationBeanPostProcessor");
        if (null != valueAnnotationBeanPostProcessor) {
            BeanDefinition beanDefinition = BeanDefinitionHolder.get(beanClass);
            valueAnnotationBeanPostProcessor.postProcessProperties(bean, BeanUtil.getBeanName(beanDefinition));
        }

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
