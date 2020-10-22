package org.spring.framework.core.util;

import java.util.Optional;

/**
 * @Author kevin xiajun94@FoxMail.com
 * @Description
 * @name Assert
 * @Date 2020/10/22 16:04
 */
public class Assert {

    public static void notNull(Object nullable, String msg){
        Optional.ofNullable(nullable).orElseThrow(() -> new NullPointerException(msg));
    }

}
