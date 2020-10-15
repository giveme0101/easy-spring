
A sample IOC framework like spring, ^^


## TODO LIST

* [x] @ComponentScan
* [x] @Component
* [x] @Autowired -> AutowiredAnnotationBeanPostProcessor -> InstantiationAwareBeanPostProcessor
* [x] @Value -> ValueAnnotationBeanPostProcessor -> InstantiationAwareBeanPostProcessor
* [x] BeanPostProcessor
* [x] FactoryBean（getObject(), getObjectType()）
* [x] InitializingBean
* [x] AOP 
```   
    1. 通过BeanPostProcessor实现
    2. 通过扩展BeanPostProcessor, 添加拦截器调用链（原理：mybatis-interceptor）
                    -> JdkAopProxyBeanPostProcessor
                    -> CglibAopProxyBeanPostProcessor
```
* [ ] 拦截器 Interception 无法被Spring管理
* [x] 构造器注入（不支持list、map、array等复合类型）
* [x] BeanNameAware
* [ ] BeanFactoryAware （无法对BeanFactory创建bean生命周期）
* [ ] ApplicationContextAware （无法对ApplicationContext创建bean生命周期）
* [ ] DisposableBean bean销毁
* [x] ApplicationEvent (容器启动完毕事件)
* [ ] CommandLineRunner, 准备基于Event添加事件回调
