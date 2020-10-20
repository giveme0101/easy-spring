package org.spring.framework.ioc;

import lombok.extern.slf4j.Slf4j;
import org.spring.framework.core.InstantiationAwareBeanPostProcessor;
import org.spring.framework.core.annotation.Autowired;
import org.spring.framework.core.aware.BeanFactoryAware;
import org.spring.framework.core.bean.BeanFactory;
import org.spring.framework.core.util.EscapeUtil;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * @Author kevin xiajun94@FoxMail.com
 * @Description
 *
 * @name AutowiredAnnotationBeanPostProcessor
 * @Date 2020/10/13 14:04
 */
@Slf4j
public class AutowiredAnnotationBeanPostProcessor implements InstantiationAwareBeanPostProcessor, BeanFactoryAware {

    private BeanFactory beanFactory;

    @Override
    public void postProcessProperties(Object bean, String beanName) {

        // 字段注入
        Field[] declaredFields = bean.getClass().getDeclaredFields();
        for (final Field field : declaredFields) {
            if (!field.isAnnotationPresent(Autowired.class)){
                continue;
            }

            try {
                field.setAccessible(true);
                Class fieldClass = field.getType();
                Object fileInstance = beanFactory.getBean(fieldClass);
                field.set(bean, fileInstance);
                log.debug("@Autowired {} {}", bean.getClass().getSimpleName(), fileInstance);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        // Setter注入
        Method[] declaredMethods = bean.getClass().getDeclaredMethods();
        for (final Method method : declaredMethods) {
            if (!method.isAnnotationPresent(Autowired.class)){
                continue;
            }

            try {
                String setterName = method.getName();
                String requiredBeanName = transSetterNameToBeanName(setterName);
                method.setAccessible(true);
                Object fileInstance = beanFactory.getBean(requiredBeanName);
                method.invoke(bean, fileInstance);
                log.debug("@Autowired {} {}", bean.getClass().getSimpleName(), fileInstance);
            } catch (Exception ex){
                ex.printStackTrace();
            }
        }
    }

    private String transSetterNameToBeanName(String setterName) {
        String beanName = setterName.substring(3);
        beanName = EscapeUtil.firstCharLowerCase(beanName);
        return beanName;
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

}
