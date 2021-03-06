package com.github.giveme0101.config;


import com.github.giveme0101.dao.IOrderMapper;
import lombok.extern.slf4j.Slf4j;
import org.spring.framework.aop.Interceptor;
import org.spring.framework.aop.MethodInvocation;

@Slf4j
public class OrderInterceptor extends Interceptor {

    @Override
    public int getOrder() {
        return 0;
    }

    @Override
    public Class getTargetClass() {
        return IOrderMapper.class;
    }

    @Override
    public Object intercept(MethodInvocation methodInvocation) {
        log.debug("orderMapper.{}({})", methodInvocation.getTargetMethod().getName(), methodInvocation.getArgs());
        Object result = methodInvocation.proceed();
        log.debug("result: {}", result);
        return result;
    }
}
