package org.spring.framework.aop;

/**
 * @author tom & shuang.kou
 * @createTime 2020年10月6日10:20:26
 */

public abstract class Interceptor {

    public int getOrder() {
        return -1;
    }

    public abstract Class getTargetClass();

    public abstract Object intercept(MethodInvocation methodInvocation);
}
