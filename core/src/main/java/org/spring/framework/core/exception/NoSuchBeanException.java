package org.spring.framework.core.exception;

/**
 * @Author kevin xiajun94@FoxMail.com
 * @Description
 * @name NoSuchBeanException
 * @Date 2020/10/21 15:39
 */
public class NoSuchBeanException extends BeansException {

    public NoSuchBeanException(String message) {
        super(message);
    }

    public NoSuchBeanException(String message, Throwable cause) {
        super(message, cause);
    }

}
