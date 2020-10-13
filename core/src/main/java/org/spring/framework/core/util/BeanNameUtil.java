package org.spring.framework.core.util;

/**
 * @Author kevin xiajun94@FoxMail.com
 * @Description
 * @name BeanNameUtil
 * @Date 2020/10/13 14:59
 */
public class BeanNameUtil {

    public static String getBeanName(Class clazz){
        return EscapeUtil.firstCharLowerCase(clazz.getSimpleName());
    }

}
