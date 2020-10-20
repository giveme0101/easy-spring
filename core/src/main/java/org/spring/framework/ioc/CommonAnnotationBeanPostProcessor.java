package org.spring.framework.ioc;

import lombok.extern.slf4j.Slf4j;
import org.spring.framework.core.InstantiationAwareBeanPostProcessor;
import org.spring.framework.core.annotation.Resource;
import org.spring.framework.core.aware.BeanFactoryAware;
import org.spring.framework.core.bean.BeanFactory;

import java.lang.reflect.Field;

/**
 * @Author kevin xiajun94@FoxMail.com
 * @Description
 *
 * @name CommonAnnotationBeanPostProcessor
 * @Date 2020/10/13 14:04
 */
@Slf4j
public class CommonAnnotationBeanPostProcessor implements InstantiationAwareBeanPostProcessor, BeanFactoryAware {

    private BeanFactory beanFactory;

    @Override
    public void postProcessProperties(Object bean, String beanName) {

        Field[] declaredFields = bean.getClass().getDeclaredFields();
        for (final Field field : declaredFields) {
            resourceFiled(field, bean);
        }
    }

    private void resourceFiled(final Field field, final Object bean){
        if (field.isAnnotationPresent(Resource.class)){

            Resource resource = field.getAnnotation(Resource.class);
            String beanName = resource.value();

            try {
                field.setAccessible(true);
                Object fileInstance = beanFactory.getBean(beanName);
                field.set(bean, fileInstance);
                log.debug("@Resource {} {}", bean.getClass().getSimpleName(), fileInstance);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }
}