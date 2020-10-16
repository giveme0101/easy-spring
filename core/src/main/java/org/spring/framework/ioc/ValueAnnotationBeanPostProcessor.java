package org.spring.framework.ioc;

import lombok.extern.slf4j.Slf4j;
import org.spring.framework.core.annotation.Value;
import org.spring.framework.core.EnvironmentResolver;
import org.spring.framework.core.InstantiationAwareBeanPostProcessor;
import org.spring.framework.core.util.ContextLoader;

import java.lang.reflect.Field;

/**
 * @Author kevin xiajun94@FoxMail.com
 * @Description
 *
 * @name ValueAnnotationBeanPostProcessor
 * @Date 2020/10/13 14:04
 */
@Slf4j
public class ValueAnnotationBeanPostProcessor implements InstantiationAwareBeanPostProcessor {

    private EnvironmentResolver environmentResolver;

    public String getProperties(String key) {
        if (null == environmentResolver){
            environmentResolver = ContextLoader.getContext();
        }

        return environmentResolver.getValue(key);
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

}
