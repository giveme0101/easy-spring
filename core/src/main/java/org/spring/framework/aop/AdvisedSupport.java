package org.spring.framework.aop;

import lombok.Getter;
import lombok.Setter;

/**
 * @Author kevin xiajun94@FoxMail.com
 * @Description
 * @name AdvisedSupport
 * @Date 2020/10/16 9:03
 */
@Setter
@Getter
public class AdvisedSupport {

    private Class targetClass;

    private Object target;

    private Interceptor interceptor;

}
