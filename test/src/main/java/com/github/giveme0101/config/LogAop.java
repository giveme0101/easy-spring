package com.github.giveme0101.config;

import com.github.giveme0101.service.IOrderService;
import lombok.extern.slf4j.Slf4j;
import org.spring.framework.core.annotation.Component;
import org.spring.framework.core.BeanPostProcessor;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @Author kevin xiajun94@FoxMail.com
 * @Description
 * @name LogAop
 * @Date 2020/09/17 14:07
 */
@Slf4j
@Component
public class LogAop implements BeanPostProcessor {

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) {

        if (bean instanceof IOrderService){
            return Proxy.newProxyInstance(LogAop.class.getClassLoader(), new Class[]{IOrderService.class}, (Object proxy, Method method, Object[] args) -> {

                log.debug("aop before: {}", method.getName());
                try {
                    Object result = method.invoke(bean, args);
                    log.debug("aop result: {}", result);
                    return result;
                } catch (Exception ex){
                    log.warn("aop error: {}", ex.getMessage());
                    throw new RuntimeException(ex);
                }
            });
        }

        return bean;
    }
}
