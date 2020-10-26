package org.spring.framework;

import org.spring.framework.core.context.AnnotationConfigApplicationContext;
import org.spring.framework.core.context.ApplicationContext;

/**
 * @Author kevin xiajun94@FoxMail.com
 * @Description
 * @name SpringApplication
 * @Date 2020/10/26 11:04
 */
public class SpringApplication {

    public static ApplicationContext run(Class configClass, String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext();
        context.run(configClass);
        return context;
    }

}
