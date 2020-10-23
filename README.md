
A sample IOC framework like spring, ^^


## TODO LIST

* [x] @ComponentScan
* [x] @Component
* [x] BeanPostProcessor
* [x] @Autowired
* [x] @Resource
* [x] @Value (支持.properties/.yml/.yaml配置文件)
* [x] FactoryBean(getObject(), getObjectType()) (getObject()方法产生的bean无法实现bean的生命周期)
* [x] InitializingBean
* [x] [AOP](#AOP) 需修改为拦截器链实现
* [x] 拦截器 Interception (暂时无法被Spring管理)
* [x] 构造器注入(不支持list、map、array等复合类型)
* [x] BeanNameAware
* [x] BeanFactoryAware
* [x] ResourceAware
* [x] ApplicationContextAware
* [x] DisposableBean bean销毁
* [x] ApplicationEvent (容器启动完毕事件)
* [x] CommandLineRunner
* [ ] 2级或3级缓存实现循环依赖
* [ ] Interceptor代理接口， @Autowire/构造器注入 接口的实现报错！
* [x] @Import (引入外部Bean)
* [ ] SPI
* [x] @Ordered
* [x] @Transactional -> TransactionalAnnotationBeanPostProcessor

### AOP 
```
1. 通过BeanPostProcessor实现
2. 通过扩展BeanPostProcessor, 添加拦截器调用链(原理：mybatis-interceptor)
        -> AopProxyBeanPostProcessor
            -> AopProxyFactory
                -> CglibAopProxy
                -> JdkAopProxy
```
