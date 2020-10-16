package org.spring.framework.core.bean;

import org.reflections.Reflections;
import org.spring.framework.core.Import;
import org.spring.framework.core.beandefinition.BeanDefinitionHolder;

import java.util.Set;

/**
 * @Author kevin xiajun94@FoxMail.com
 * @Description
 * @name ImportClassImport
 * @Date 2020/10/16 10:32
 */
public class ImportClassImporter {

    public static void importClass(String scanPackage){

        Set<Class<?>> importAnnoClassSet = new Reflections(scanPackage).getTypesAnnotatedWith(Import.class);
        for (final Class<?> importAnnoClass : importAnnoClassSet) {
            for (final Class importClass : importAnnoClass.getAnnotation(Import.class).value()) {
                BeanDefinitionHolder.put(BeanDefinitionParser.parse(importClass));
            }
        }
    }

}
