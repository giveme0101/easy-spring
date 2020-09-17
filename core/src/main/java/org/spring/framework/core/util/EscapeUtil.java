package org.spring.framework.core.util;

/**
 * @Author kevin xiajun94@FoxMail.com
 * @Description
 * @name EscapeUtil
 * @Date 2020/09/17 11:23
 */
public class EscapeUtil {

    public static String firstCharLowerCase(String str){
        String firstChar = str.substring(0, 1).toLowerCase();
        return firstChar + str.substring(1);
    }

}
