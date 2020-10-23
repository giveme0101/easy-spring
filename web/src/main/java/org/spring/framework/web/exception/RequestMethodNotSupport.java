package org.spring.framework.web.exception;

/**
 * @Author kevin xiajun94@FoxMail.com
 * @Description
 * @name RequestMethodNotSupport
 * @Date 2020/10/23 16:59
 */
public class RequestMethodNotSupport extends RuntimeException {

    public RequestMethodNotSupport() {
    }

    public RequestMethodNotSupport(String message) {
        super(message);
    }

    public RequestMethodNotSupport(String message, Throwable cause) {
        super(message, cause);
    }

    public RequestMethodNotSupport(Throwable cause) {
        super(cause);
    }

    public RequestMethodNotSupport(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
