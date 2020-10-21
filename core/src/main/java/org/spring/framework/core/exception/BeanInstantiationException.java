package org.spring.framework.core.exception;

/**
 * @Author kevin xiajun94@FoxMail.com
 * @Description
 * @name BeanInstantiationException
 * @Date 2020/10/21 15:42
 */
public class BeanInstantiationException extends BeansException {

    public BeanInstantiationException(String message) {
        super(message);
    }

    public BeanInstantiationException(String message, Throwable cause) {
        super(message, cause);
    }
}
