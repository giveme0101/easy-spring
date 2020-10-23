package org.spring.framework.core.beans;

import lombok.extern.slf4j.Slf4j;
import org.spring.framework.core.BeanPostProcessor;
import org.spring.framework.core.InitializingBean;
import org.spring.framework.core.InstantiationAwareBeanPostProcessor;
import org.spring.framework.core.aware.ApplicationContextAware;
import org.spring.framework.core.aware.BeanFactoryAware;
import org.spring.framework.core.aware.BeanNameAware;
import org.spring.framework.core.aware.ResourceAware;
import org.spring.framework.core.bd.BeanDefinitionRegistry;
import org.spring.framework.core.bd.RootBeanDefinition;
import org.spring.framework.core.config.ResourceManager;
import org.spring.framework.core.context.ApplicationContext;
import org.spring.framework.core.util.AnnotationAwareOrderComparator;
import org.spring.framework.core.util.Assert;
import org.spring.framework.core.util.BeanNameUtil;

import java.io.IOException;
import java.lang.reflect.*;
import java.util.*;

/**
 * @Author kevin xiajun94@FoxMail.com
 * @Description
 * @name DefaultListableBeanFactory
 * @Date 2020/09/17 13:29
 */
@Slf4j
public class DefaultListableBeanFactory extends AbstractBeanFactory {

    private ApplicationContext applicationContext;

    public DefaultListableBeanFactory(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Override
    public Object getBean(String beanName) {
        RootBeanDefinition beanDefinition = BeanDefinitionRegistry.get(beanName);
        if (null == beanDefinition){
            return null;
        }

        if (beanDefinition.getIsPrototype()) {
            return createBean(beanName, beanDefinition);
        }

        if (beanDefinition.getIsLazyInit()) {
            Object bean = createBean(beanName, beanDefinition);
            this.registerSingleton(beanName, bean);
            return bean;
        }

        Object bean = this.getSingleton(beanName);
        if (null == bean){
           return createBean(beanName, beanDefinition);
        }

        return bean;
    }

    @Override
    public <T> T getBean(Class<T> beanClass) {

        RootBeanDefinition beanDefinition = BeanDefinitionRegistry.get(beanClass);
        if (null == beanDefinition){
            return null;
        }

        String beanName = BeanNameUtil.getBeanName(beanDefinition);
        return (T) getBean(beanName);
    }

    @Override
    public <T> Map<String, T> getBeansOfType(Class<T> beanClass) {

        Collection<RootBeanDefinition> beansOfType = BeanDefinitionRegistry.getBeansOfType(beanClass);
        Map<String, T> resultMap = new HashMap<>(beansOfType.size());

        for (final RootBeanDefinition bd : beansOfType) {
            String beanName = BeanNameUtil.getBeanName(bd);
            Object bean = getBean(beanName);
            resultMap.put(beanName, (T) bean);
        }

        return resultMap;
    }

    @Override
    public void refresh() {
        Map<String, RootBeanDefinition> beanDefinitionMap = BeanDefinitionRegistry.getBeanDefinitionMap();
        for (final Map.Entry<String, RootBeanDefinition> entry : beanDefinitionMap.entrySet()) {

            String beanName = entry.getKey();
            RootBeanDefinition bd = entry.getValue();

            if (bd.getIsPrototype() || bd.getIsLazyInit()){
                continue;
            }

            if (this.containsSingleton(beanName)){
                continue;
            }

            Object bean = createBean(beanName, bd);
            this.registerSingleton(beanName, bean);
        }
    }

    @Override
    public void destroySingletons() {
        super.destroySingletons();
    }

    private Object createBean(String beanName, RootBeanDefinition bd) {

        if (bd.getIsFactoryBean()){

            Class<?> factoryBeanClass = bd.getBeanClass();
            Type type = Arrays.stream(factoryBeanClass.getGenericInterfaces()).filter(i -> FactoryBean.class.isAssignableFrom(factoryBeanClass))
                    .findFirst().get();
            Class productBeanClass = (Class)((ParameterizedType) type).getActualTypeArguments()[0];
            String productBeanName = BeanNameUtil.getFactoryBeanProductBeanName(bd);

            // 创建工厂bean
            String factoryBeanName = FactoryBean.BEAN_NAME_PREFIX + productBeanName;
            FactoryBean factoryBean = (FactoryBean) doCreateBean(factoryBeanName, bd);
            BeanDefinitionRegistry.put(factoryBeanName, bd);
            this.registerSingleton(factoryBeanName, factoryBean);

            // 创建工厂bean生产的bean
            RootBeanDefinition beanDefinition = new RootBeanDefinition();
            beanDefinition.setBeanClass(productBeanClass);
            beanDefinition.setIsSingleton(factoryBean.isSingleton());
            BeanDefinitionRegistry.put(productBeanName, beanDefinition);
            if (factoryBean.isSingleton()){
                this.registerSingleton(productBeanName, factoryBean.getObject());
            }

            return factoryBean;
        } else {
            Object bean = doCreateBean(beanName, bd);
            if (bean instanceof BeanPostProcessor){
                this.addBeanPostProcessor((BeanPostProcessor) bean);
            }
            return bean;
        }
    }

    /**
     * org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory#createBean
     * 1. bean = resolveBeforeInstantiation()
     *        1.1   applyBeanPostProcessorsBeforeInstantiation() -> bean = InstantiationAwareBeanPostProcessor#postProcessBeforeInstantiation
     *        1.2   applyBeanPostProcessorsAfterInitialization() -> bean = BeanPostProcessor#postProcessAfterInitialization
     *      if bean != null, 返回bean
     * 2. doCreateBean
     *     2.1. createBeanInstance() -> instantiateBean() -> InstantiationStrategy#instantiate
     *     2.2. applyMergedBeanDefinitionPostProcessors() -> MergedBeanDefinitionPostProcessor#postProcessMergedBeanDefinition
     *     2.3. populateBean()
     *          2.3.1   InstantiationAwareBeanPostProcessor#postProcessAfterInstantiation 如果返回false，返回
     *          2.3.2   autowireByName()
     *          2.3.3   autowireByType()
     *          2.3.4   InstantiationAwareBeanPostProcessor.postProcessPropertyValues 进行DI注入
     *                  2.3.4.1    AutowiredAnnotationBeanPostProcessor (@Autowired、@Value、@Inject)
     *                  2.3.4.2    CommonAnnotationBeanPostProcessor (@PostConstruct、@PreDestroy、@Resource)
     *     2.4. initializeBean()
     *          2.4.1   invokeAwareMethods()
     *                  2.4.1.1     BeanNameAware、BeanClassLoaderAware、BeanFactoryAware
     *                  2.4.1.2     applyBeanPostProcessorsBeforeInitialization() -> BeanPostProcessor#postProcessBeforeInitialization
     *                  2.4.1.3     invokeInitMethods()
     *                              2.4.1.3.1   InitializingBean#afterPropertiesSet
     *                              2.4.1.3.2   invokeCustomInitMethod() 执行自定义init-method
     *                  2.4.1.4     applyBeanPostProcessorsAfterInitialization() -> BeanPostProcessor#postProcessAfterInitialization
     *     2.5. registerDisposableBeanIfNecessary() 如果bean实现DisposableBean接口，将bean保存到disposableBeans中，Spring容器关闭时回调
     */
    private Object doCreateBean(String beanName, RootBeanDefinition bd) {

        // 1. 实例化
        Object bean = createBeanInstance(bd);

        // 2. 设置属性
        populateBean(beanName, bd, bean);

        // 3. 初始化
        bean = initializeBean(beanName, bean);

        // 4. 注册bean销毁方法
        registerDisposableBeanIfNecessary(beanName, bean);

        return bean;
    }

    private void registerDisposableBeanIfNecessary(String beanName, Object bean){
        if (bean instanceof DisposableBean){
            registerDisposableBean(beanName, (DisposableBean) bean);
        }
    }

    private Object createBeanInstance(RootBeanDefinition bd){

        Class beanClass = bd.getBeanClass();

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
                        log.warn("获取bean【{}】失败, 执行下一构造方法", type);
                        continue;
                    }

                    params.add(bean);
                }

                instance = constructor.newInstance(params.toArray());
            }

            if (InstantiationAwareBeanPostProcessor.class.isAssignableFrom(beanClass)) {
                // TODO 调用后置处理器 return postProcessAfterInstantiation();
            }

            Assert.notNull(instance, "创建bean失败：构造器注入失败");
            return instance;
        } catch (IllegalAccessException | InstantiationException | InvocationTargetException | IllegalArgumentException e) {
            e.printStackTrace();
        }

        return null;
    }

    private void populateBean(String beanName, RootBeanDefinition bd, Object bean) {

        // aware
        doAwareMethod(bean, beanName, bd.getBeanClass());

        // 设置属性
        // NOP

        // @Autowired、@Value 等注解生效
        if  (!(bean instanceof InstantiationAwareBeanPostProcessor)) {

            PropertyValues pvs = bd.getPropertyValues();
            boolean continueWithPropertyPopulation = true;

            List<BeanPostProcessor> beanPostProcessors = getBeanPostProcessors();
            AnnotationAwareOrderComparator.sort(beanPostProcessors);

            for (final BeanPostProcessor processor : beanPostProcessors) {
                if (processor instanceof InstantiationAwareBeanPostProcessor){
                    if (!((InstantiationAwareBeanPostProcessor)processor).postProcessAfterInstantiation(bean, beanName)) {
                        continueWithPropertyPopulation = false;
                        break;
                    }
                }
            }

            if (!continueWithPropertyPopulation){
                return;
            }

            for (final BeanPostProcessor processor : beanPostProcessors) {
                if (processor instanceof InstantiationAwareBeanPostProcessor) {
                    pvs = ((InstantiationAwareBeanPostProcessor) processor).postProcessPropertyValues(pvs, bean, BeanNameUtil.getBeanName(bd));
                }
            }

            if (pvs == null){
                return;
            }

            applyPropertyValues(beanName, bd, bean);
        }
    }

    private void doAwareMethod(Object bean, String beanName, Class<?> beanClass) {
        if (bean instanceof ApplicationContextAware){
            log.debug("ApplicationContextAware: {}", beanClass.getName());
            ((ApplicationContextAware) bean).setApplicationContext(applicationContext);
        }
        if (bean instanceof BeanFactoryAware){
            log.debug("BeanFactoryAware: {}", beanClass.getName());
            ((BeanFactoryAware) bean).setBeanFactory(this);
        }
        if (bean instanceof BeanNameAware){
            log.debug("BeanNameAware: {}", beanClass.getName());
            ((BeanNameAware) bean).setBeanName(beanName);
        }
        if (bean instanceof ResourceAware){
            log.debug("ResourceAware: {}", beanClass.getName());
            try {
                ResourceManager resourceManager = getBean(ResourceManager.class);
                ((ResourceAware) bean).setResources(resourceManager.loadProperties());
            } catch (IOException ex){
                log.error(ex.getMessage(), ex);
                System.exit(-1);
            }
        }
    }

    private void applyPropertyValues(String beanName, RootBeanDefinition bd, Object bean){
        // NOP
    }

    private Object initializeBean(String beanName, Object bean) {

        // BeanPostProcessor beanPostBeforeInitialization
        bean = beanPostBeforeInitialization(bean, beanName);

        if (bean instanceof InitializingBean){
            log.debug("invoke InitializingBean.afterPropertiesSet(): {}", bean);
            ((InitializingBean) bean).afterPropertiesSet();
        }

        // BeanPostProcessor beanPostAfterInitialization
        bean = beanPostAfterInitialization(bean, beanName);

        return bean;
    }

    private Object beanPostBeforeInitialization(Object bean, String beanName) {

        Object postBean = bean;

        List<BeanPostProcessor> beanPostProcessors = getBeanPostProcessors();
        AnnotationAwareOrderComparator.sort(beanPostProcessors);

        for (final BeanPostProcessor postProcessor : beanPostProcessors) {
            postBean = postProcessor.postProcessBeforeInitialization(postBean, beanName);
        }

        return postBean;
    }

    private Object beanPostAfterInitialization(Object bean, String beanName) {

        Object postBean = bean;

        List<BeanPostProcessor> beanPostProcessors = getBeanPostProcessors();
        AnnotationAwareOrderComparator.sort(beanPostProcessors);

        for (final BeanPostProcessor postProcessor : beanPostProcessors) {
            postBean = postProcessor.postProcessAfterInitialization(postBean, beanName);
        }

        return postBean;
    }
}
