package org.spring.framework.core.bean;

import lombok.extern.slf4j.Slf4j;
import org.spring.framework.core.BeanPostProcessor;
import org.spring.framework.core.InitializingBean;
import org.spring.framework.core.InstantiationAwareBeanPostProcessor;
import org.spring.framework.core.annotation.Ordered;
import org.spring.framework.core.aware.ApplicationContextAware;
import org.spring.framework.core.aware.BeanFactoryAware;
import org.spring.framework.core.aware.BeanNameAware;
import org.spring.framework.core.bd.BeanDefinition;
import org.spring.framework.core.bd.BeanDefinitionHolder;
import org.spring.framework.core.context.ApplicationContext;
import org.spring.framework.core.util.BeanNameUtil;
import org.spring.framework.ioc.AutowiredAnnotationBeanPostProcessor;
import org.spring.framework.ioc.ValueAnnotationBeanPostProcessor;

import java.lang.reflect.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * @Author kevin xiajun94@FoxMail.com
 * @Description
 * @name DefaultListableBeanFactory
 * @Date 2020/09/17 13:29
 */
@Slf4j
public class DefaultListableBeanFactory implements BeanFactory {

    private ApplicationContext applicationContext;
    private Map<String, Object> singletonObjects = new ConcurrentHashMap<>();

    public DefaultListableBeanFactory(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

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

        String beanName = BeanNameUtil.getBeanName(beanDefinition);
        return (T) getBean(beanName);
    }

    @Override
    public <T> Map<String, T> getBeansOfType(Class<T> beanClass) {

        Collection<BeanDefinition> beansOfType = BeanDefinitionHolder.getBeansOfType(beanClass);
        Map<String, T> resultMap = new HashMap<>(beansOfType.size());

        for (final BeanDefinition bd : beansOfType) {
            String beanName = BeanNameUtil.getBeanName(bd);
            Object bean = getBean(beanName);
            resultMap.put(beanName, (T) bean);
        }

        return resultMap;
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
            String productBeanName = BeanNameUtil.getFactoryBeanProductBeanName(bd);

            // 创建工厂bean
            String factoryBeanName = FactoryBean.BEAN_NAME_PREFIX + productBeanName;
            FactoryBean factoryBean = (FactoryBean) createBean(factoryBeanName, bd);
            BeanDefinitionHolder.put(factoryBeanName, bd);
            singletonObjects.put(factoryBeanName, factoryBean);

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

        // aware
        doAwareMethod(bean, beanName, beanClass);

        // 2. 设置属性
        populateBean(bean, beanClass);

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
        } catch (IllegalAccessException | InstantiationException | InvocationTargetException | IllegalArgumentException e) {
            e.printStackTrace();
        }

        return null;
    }

    private void populateBean(Object bean, Class<?> beanClass) {

        // 设置属性
        // NOP

        // 执行@Autowried注入
        InstantiationAwareBeanPostProcessor autowiredAnnotationBeanPostProcessor = (AutowiredAnnotationBeanPostProcessor) singletonObjects.get("autowiredAnnotationBeanPostProcessor");
        if (null != autowiredAnnotationBeanPostProcessor) {
            BeanDefinition beanDefinition = BeanDefinitionHolder.get(beanClass);
            autowiredAnnotationBeanPostProcessor.postProcessProperties(bean, BeanNameUtil.getBeanName(beanDefinition));
        }

        // 执行@Value注入
        InstantiationAwareBeanPostProcessor valueAnnotationBeanPostProcessor = (ValueAnnotationBeanPostProcessor) singletonObjects.get("valueAnnotationBeanPostProcessor");
        if (null != valueAnnotationBeanPostProcessor) {
            BeanDefinition beanDefinition = BeanDefinitionHolder.get(beanClass);
            valueAnnotationBeanPostProcessor.postProcessProperties(bean, BeanNameUtil.getBeanName(beanDefinition));
        }
    }

    private void doAwareMethod(Object bean, String beanName, Class<?> beanClass) {
        if (bean instanceof ApplicationContextAware){
            log.debug("invoke ApplicationContextAware: {}", beanClass.getName());
            ((ApplicationContextAware) bean).setApplicationContext(applicationContext);
        }
        if (bean instanceof BeanFactoryAware){
            log.debug("invoke BeanFactoryAware: {}", beanClass.getName());
            ((BeanFactoryAware) bean).setBeanFactory(this);
        }
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

        Collection<BeanPostProcessor> values = singletonObjects.values().stream()
                .filter(o -> o instanceof BeanPostProcessor)
                .map(BeanPostProcessor.class::cast)
                .sorted((a, b) -> {

                    int ao = 0, bo = 0;

                    Ordered aa = a.getClass().getAnnotation(Ordered.class);
                    Ordered ba = b.getClass().getAnnotation(Ordered.class);

                    if (aa != null){
                        ao = aa.value();
                    }

                    if (ba != null){
                        bo = ba.value();
                    }

                    return bo - ao;
                })
                .collect(Collectors.toList());

        for (final BeanPostProcessor postProcessor : values ) {
                postBean = postProcessor.postProcessAfterInitialization(postBean, beanName);
        }

        return postBean;
    }

    private Object beanPostBeforeInitialization(Object bean, String beanName) {

        Object postBean = bean;
        for (final Object object : singletonObjects.values()) {
            if (object instanceof BeanPostProcessor){
                BeanPostProcessor postProcessor = (BeanPostProcessor) object;
                postBean = postProcessor.postProcessBeforeInitialization(postBean, beanName);
            }
        }

        return postBean;
    }
}
