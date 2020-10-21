package org.spring.framework.core.exception;

/**
 * @Author kevin xiajun94@FoxMail.com
 * @Description
 * @name NoSuchBeanDefinitionException
 * @Date 2020/10/21 15:39
 */
public class NoSuchBeanDefinitionException extends BeansException {

    public NoSuchBeanDefinitionException(String message) {
        super(message);
    }

    public NoSuchBeanDefinitionException(String message, Throwable cause) {
        super(message, cause);
    }

}
