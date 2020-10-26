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
 * @name ImportClassImport
 * @Date 2020/10/16 10:32
 */
public class ImportClassImporter {

    public static void importClass(String scanPackage) {

        Set<Class> importAnnoClassSet = scanPackage(scanPackage);
        for (final Class<?> importAnnoClass : importAnnoClassSet) {

            Annotation[] annotations = importAnnoClass.getAnnotations();
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
    }

    public static Set<Class> scanPackage(String scanPackage){
        return ClassScanner.scan(scanPackage, (obj) -> {
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
    }
}
