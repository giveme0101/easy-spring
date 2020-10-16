package org.spring.framework.core.util;

import org.spring.framework.core.beandefinition.BeanDefinition;
import org.spring.framework.core.bean.FactoryBean;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;

/**
 * @Author kevin xiajun94@FoxMail.com
 * @Description
 * @name BeanNameUtil
 * @Date 2020/10/13 14:59
 */
public class BeanNameUtil {

    public static String getBeanName(BeanDefinition bd){
        return EscapeUtil.firstCharLowerCase(bd.getBeanClass().getSimpleName());
    }

    public static String getFactoryBeanProductBeanName(BeanDefinition bd){

        Class beanClass = bd.getBeanClass();
        Type type = Arrays.stream(beanClass.getGenericInterfaces()).filter(i -> FactoryBean.class.isAssignableFrom(beanClass))
                .findFirst().get();
        Class actualTypeArguments = (Class)((ParameterizedType) type).getActualTypeArguments()[0];

        return EscapeUtil.firstCharLowerCase(actualTypeArguments.getSimpleName());
    }

}
