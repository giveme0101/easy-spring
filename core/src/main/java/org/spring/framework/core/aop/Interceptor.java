package org.spring.framework.core.aop;

/**
 * @author tom & shuang.kou
 * @createTime 2020年10月6日10:20:26
 */

public abstract class Interceptor {

    public int getOrder() {
        return -1;
    }

    public boolean supports(Object bean) {
        return true;
    }

    public abstract Object intercept(MethodInvocation methodInvocation);
}
