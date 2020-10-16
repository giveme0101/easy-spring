package com.github.giveme0101.config;


import lombok.extern.slf4j.Slf4j;
import org.spring.framework.aop.Interceptor;
import org.spring.framework.aop.MethodInvocation;
import org.spring.framework.jdbc.JdbcTemplate;

@Slf4j
public class JdbcLoggerInterceptor extends Interceptor {

    @Override
    public int getOrder() {
        return 0;
    }

    @Override
    public Class getTargetClass() {
        return JdbcTemplate.class;
    }

    @Override
    public Object intercept(MethodInvocation methodInvocation) {
        log.info("SQL：{}", methodInvocation.getArgs()[0]);
        log.info("param：{}", methodInvocation.getArgs()[1]);
        Object result = methodInvocation.proceed();
        log.info("result：{}", result);
        return result;
    }
}
