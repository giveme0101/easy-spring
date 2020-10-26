package org.spring.framework.core.beans;

import org.spring.framework.core.annotation.Import;
import org.spring.framework.core.bd.BeanDefinitionParser;
import org.spring.framework.core.bd.BeanDefinitionRegistry;
import org.spring.framework.core.util.ClassScanner;

import java.lang.annotation.Annotation;
import java.util.Set;

/**
 * @Author kevin xiajun94@FoxMail.com
 * @Description
 * @name ImportClassLoader
 * @Date 2020/10/16 10:32
 */
public class ImportClassLoader {

    public static void loadClass(Class scanClazz){

        Annotation[] annotations = scanClazz.getAnnotations();
        for (final Annotation annotation : annotations) {
            Class<? extends Annotation> annotationType = annotation.annotationType();

            if (Import.class == annotationType){
                Import importAnno = (Import) annotation;
                for (final Class importClass : importAnno.value()) {
                    BeanDefinitionRegistry.put(BeanDefinitionParser.parse(importClass));
                }
            }

            if (annotationType.isAnnotationPresent(Import.class)){
                Import importAnno = annotationType.getAnnotation(Import.class);
                for (final Class importClass : importAnno.value()) {
                    BeanDefinitionRegistry.put(BeanDefinitionParser.parse(importClass));
                }
            }
        }

    }

    public static void loadPackage(String scanPackage) {

        Set<Class> importAnnoClassSet = ClassScanner.scan(scanPackage, (obj) -> {
            Class clazz = (Class) obj;

            Annotation[] annotations = clazz.getAnnotations();
            for (final Annotation annotation : annotations) {
                Class<? extends Annotation> annotationType = annotation.annotationType();
                if (Import.class == annotationType || annotationType.isAnnotationPresent(Import.class)){
                    return true;
                }
            }

            return false;
        });

        for (final Class<?> importAnnoClass : importAnnoClassSet) {
            loadClass(importAnnoClass);
        }
    }
}
