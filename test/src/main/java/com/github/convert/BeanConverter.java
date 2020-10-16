package com.github.convert;

import net.sf.cglib.beans.BeanCopier;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author kevin xiajun94@FoxMail.com
 * @Description
 * @name BeanConverter
 * @Date 2020/10/16 10:55
 */
public class BeanConverter implements Converter {

    private static final ConcurrentHashMap<String, BeanCopier> BEAN_COPIER_CACHE = new ConcurrentHashMap<>();

    @Override
    public void convert(Object source, Object target) {

        Class targetClass = target.getClass();
        String key = genKey(source.getClass(), targetClass);

        BeanCopier beanCopier;
        if (BEAN_COPIER_CACHE.containsKey(key)) {
            beanCopier = BEAN_COPIER_CACHE.get(key);
        } else {
            beanCopier = BeanCopier.create(source.getClass(), targetClass, false);
            BEAN_COPIER_CACHE.put(key, beanCopier);
        }

        beanCopier.copy(source, target, null);
    }

    private static String genKey(Class<?> srcClazz, Class<?> tgtClazz) {
        return srcClazz.getName() + tgtClazz.getName();
    }

}
