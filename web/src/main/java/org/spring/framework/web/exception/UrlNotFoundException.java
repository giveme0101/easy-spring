package org.spring.framework.web.exception;

/**
 * @Author kevin xiajun94@FoxMail.com
 * @Description
 * @name NotFoundException
 * @Date 2020/10/23 16:24
 */
public class UrlNotFoundException extends RuntimeException {

    public UrlNotFoundException() {
    }

    public UrlNotFoundException(String message) {
        super(message);
    }

    public UrlNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public UrlNotFoundException(Throwable cause) {
        super(cause);
    }

    public UrlNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
