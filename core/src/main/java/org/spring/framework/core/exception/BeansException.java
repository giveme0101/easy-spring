package org.spring.framework.core.exception;

/**
 * @Author kevin xiajun94@FoxMail.com
 * @Description
 * @name BeansException
 * @Date 2020/10/21 15:38
 */
public class BeansException extends RuntimeException {

    public BeansException(String message) {
        super(message);
    }

    public BeansException(String message, Throwable cause) {
        super(message, cause);
    }

}
