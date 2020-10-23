package org.spring.framework.ioc;

import lombok.extern.slf4j.Slf4j;
import org.spring.framework.core.InstantiationAwareBeanPostProcessor;
import org.spring.framework.core.annotation.Autowired;
import org.spring.framework.core.annotation.Value;
import org.spring.framework.core.aware.BeanFactoryAware;
import org.spring.framework.core.beans.BeanFactory;
import org.spring.framework.core.beans.PropertyValues;
import org.spring.framework.core.aware.EmbeddedValueResolverAware;
import org.spring.framework.core.config.StringValueResolver;
import org.spring.framework.core.util.Assert;
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
public class AutowiredAnnotationBeanPostProcessor implements InstantiationAwareBeanPostProcessor, BeanFactoryAware, EmbeddedValueResolverAware {

    private BeanFactory beanFactory;
    private StringValueResolver stringValueResolver;

    @Override
    public PropertyValues postProcessPropertyValues(PropertyValues pvs, Object bean, String beanName) {

        // 字段注入
        Field[] declaredFields = bean.getClass().getDeclaredFields();
        for (final Field field : declaredFields) {
            autowiredFiled(field, bean);
            valueFiled(field, bean);
        }

        // Setter注入
        Method[] declaredMethods = bean.getClass().getDeclaredMethods();
        for (final Method method : declaredMethods) {
            autowiredMethod(method, bean);
        }

        return pvs;
    }

    private void autowiredFiled(final Field field, final Object bean){
        if (field.isAnnotationPresent(Autowired.class)){
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
    }

    private void autowiredMethod(final Method method, final Object bean){
        if (method.isAnnotationPresent(Autowired.class)){
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

    private void valueFiled(final Field field, final Object bean){
        if (field.isAnnotationPresent(Value.class)){
            Value annotation = field.getAnnotation(Value.class);
            String key = annotation.value();

            try {
                String value = getConfigValueOfDefault(key);
                Assert.notNull(value, "获取配置【" + key + "】失败");

                field.setAccessible(true);
                field.set(bean, value);
                log.debug("@Value {} {}", bean.getClass().getSimpleName(), key);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    private String getConfigValueOfDefault(String key){

        key = key.replace("${", "").replace("}", "");
        String[] defineKey = key.split(":");

        String value = stringValueResolver.resolveStringValue(defineKey[0]);

        return value == null && defineKey.length == 2 ? defineKey[1] : value;
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

    @Override
    public Object postProcessBeforeInstantiation(Class<?> beanClass, String beanName) {
        return null;
    }

    @Override
    public boolean postProcessAfterInstantiation(Object bean, String beanName) {
        return true;
    }

    @Override
    public void setEmbeddedValueResolver(StringValueResolver resolver) {
        this.stringValueResolver = resolver;
    }
}
