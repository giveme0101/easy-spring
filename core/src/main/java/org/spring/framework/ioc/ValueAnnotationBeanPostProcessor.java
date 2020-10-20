package org.spring.framework.ioc;

import lombok.extern.slf4j.Slf4j;
import org.spring.framework.core.InstantiationAwareBeanPostProcessor;
import org.spring.framework.core.annotation.Value;
import org.spring.framework.core.aware.EnvironmentAware;

import java.lang.reflect.Field;
import java.util.Properties;

/**
 * @Author kevin xiajun94@FoxMail.com
 * @Description
 *
 * @name ValueAnnotationBeanPostProcessor
 * @Date 2020/10/13 14:04
 */
@Slf4j
public class ValueAnnotationBeanPostProcessor implements InstantiationAwareBeanPostProcessor, EnvironmentAware {

    private Properties properties;

    public String getProperties(String key) {
        return properties.getProperty(key);
    }

    @Override
    public void postProcessProperties(Object bean, String beanName) {

        Field[] declaredFields = bean.getClass().getDeclaredFields();
        for (final Field field : declaredFields) {
            if (!field.isAnnotationPresent(Value.class)){
                continue;
            }

            Value annotation = field.getAnnotation(Value.class);
            String key = annotation.value();

            try {
                field.setAccessible(true);
                field.set(bean, getProperties(key));
                log.debug("@Value {} {}", bean.getClass().getSimpleName(), key);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void setProperties(Properties properties) {
        this.properties = properties;
    }
}
