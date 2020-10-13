package org.spring.framework.core.util;

import org.spring.framework.core.context.ApplicationContext;

/**
 * @Author kevin xiajun94@FoxMail.com
 * @Description
 * @name ContextLoader
 * @Date 2020/10/13 15:03
 */
public class ContextLoader {

    private static ApplicationContext CONTEXT;

    public static void put(ApplicationContext context){
        CONTEXT = context;
    }

    public static ApplicationContext getContext() {
        return CONTEXT;
    }
}
