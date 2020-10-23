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

    @Value("${spring.datasource.driverClassName}")
    private String driverClassName;
    @Value("${spring.datasource.url}")
    private String url;
    @Value("${spring.datasource.username}")
    private String userName;
    @Value("${spring.datasource.password}")
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
