package org.spring.framework.jdbc;

import lombok.extern.slf4j.Slf4j;
import org.spring.framework.core.annotation.Value;
import org.spring.framework.core.beans.FactoryBean;

/**
 * @Author kevin xiajun94@FoxMail.com
 * @Description
 * @name ConnectionFactoryBean
 * @Date 2020/10/13 17:03
 */
@Slf4j
public class ConnectionFactoryBean implements FactoryBean<PooledConnectionFactory> {

    @Value("datasource.driverClassName")
    private String driverClassName;
    @Value("datasource.url")
    private String url;
    @Value("datasource.username")
    private String userName;
    @Value("datasource.password")
    private String password;

    @Override
    public PooledConnectionFactory getObject() {
        return new ProxyConnectionFactory(driverClassName, url, userName, password);
    }

    @Override
    public Class<PooledConnectionFactory> getObjectType() {
        return PooledConnectionFactory.class;
    }

}
