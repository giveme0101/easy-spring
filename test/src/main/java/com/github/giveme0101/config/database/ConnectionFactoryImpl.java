package com.github.giveme0101.config.database;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * @Author kevin xiajun94@FoxMail.com
 * @Description
 * @name ConnectionFactory
 * @Date 2020/10/14 11:12
 */
public class ConnectionFactoryImpl implements ConnectionFactory {

    private final String driverClassName;
    private final String url;
    private final String username;
    private final String password;


    public ConnectionFactoryImpl(String driverClassName, String url, String username, String password) {
        this.driverClassName = driverClassName;
        this.url = url;
        this.username = username;
        this.password = password;

        try {
            Class.forName(this.driverClassName);
        } catch (ClassNotFoundException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public Connection getConnection() {
        try {
            return DriverManager.getConnection(url, username, password);
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }
}
