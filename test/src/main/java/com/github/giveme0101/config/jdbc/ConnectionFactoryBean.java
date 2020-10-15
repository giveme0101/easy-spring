package com.github.giveme0101.config.jdbc;

import lombok.extern.slf4j.Slf4j;
import org.spring.framework.core.Component;
import org.spring.framework.core.Value;
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

    @Value("datasource.driverClassName")
    private String driverClassName;
    @Value("datasource.url")
    private String url;
    @Value("datasource.username")
    private String userName;
    @Value("datasource.password")
    private String password;

    @Override
    public ConnectionFactory getObject() {
        return new ConnectionFactoryImpl(driverClassName, url, userName, password);
    }

    @Override
    public Class<ConnectionFactory> getObjectType() {
        return ConnectionFactory.class;
    }

}
