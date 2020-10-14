package com.github.giveme0101.config;

import lombok.extern.slf4j.Slf4j;
import org.spring.framework.core.Component;
import org.spring.framework.core.bean.FactoryBean;

/**
 * @Author kevin xiajun94@FoxMail.com
 * @Description
 * @name ConnectionFactoryBean
 * @Date 2020/10/13 17:03
 */
@Slf4j
@Component
public class ConnectionFactoryBean implements FactoryBean<ConnectionFactory> {

    @Override
    public ConnectionFactory getObject() {
        return new ConnectionFactoryImpl();
    }

    @Override
    public Class<ConnectionFactory> getObjectType() {
        return ConnectionFactory.class;
    }

}
