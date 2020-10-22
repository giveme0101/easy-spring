package org.spring.framework.jdbc.tm;

import org.reflections.ReflectionUtils;
import org.spring.framework.aop.AdvisedSupport;
import org.spring.framework.aop.DefaultAopProxyFactory;
import org.spring.framework.aop.Interceptor;
import org.spring.framework.aop.MethodInvocation;
import org.spring.framework.core.BeanPostProcessor;
import org.spring.framework.core.annotation.Order;
import org.spring.framework.core.aware.ApplicationContextAware;
import org.spring.framework.core.context.ApplicationContext;

import java.lang.reflect.Method;
import java.util.Set;

/**
 * @Author kevin xiajun94@FoxMail.com
 * @Description
 * @name TransactionalBeanPostProcessor
 * @Date 2020/10/16 15:30
 */
// 保证第一个代理，防止被jdk代理后无法识别
@Order(value = Integer.MAX_VALUE)
public class TransactionalAnnotationBeanPostProcessor implements BeanPostProcessor, ApplicationContextAware {

    private ApplicationContext applicationContext;

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) {

        Set<Method> methods = ReflectionUtils.getAllMethods(bean.getClass(), (method) -> {
            return method.isAnnotationPresent(Transactional.class);
        });

        if (methods.isEmpty()){
            return bean;
        }

        AdvisedSupport support = new AdvisedSupport();
        support.setTarget(bean);
        support.setTargetClass(bean.getClass());
        support.setInterceptor(new Interceptor() {
            @Override
            public Class getTargetClass() {
                return bean.getClass();
            }

            @Override
            public Object intercept(MethodInvocation methodInvocation) {

                Method targetMethod = methodInvocation.getTargetMethod();
                if (!targetMethod.isAnnotationPresent(Transactional.class)){
                    return methodInvocation.proceed();
                }

                Object proceed = null;

                try {
                    getTransactionManager().openTransaction();
                    proceed = methodInvocation.proceed();
                    getTransactionManager().commit();
                } catch (Exception ex){
                    getTransactionManager().rollback();
                    throw ex;
                } finally {
                    getTransactionManager().closeTransaction();
                }

                return proceed;
            }
        });

        return new DefaultAopProxyFactory().createAopProxy(support).getProxy();
    }

    private TransactionManager getTransactionManager() {
        return applicationContext.getBean(TransactionManager.class);
    }

    @Override
    public void setApplicationContext(ApplicationContext context) {
        this.applicationContext = context;
    }
}
