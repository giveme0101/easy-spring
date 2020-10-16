package org.spring.framework.jdbc;

import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * @Author kevin xiajun94@FoxMail.com
 * @Description
 * @name JdbcConnectionFactory
 * @Date 2020/10/14 11:12
 */
@Slf4j
public class JdbcConnectionFactory implements ConnectionFactory {

    private final String driverClassName;
    private final String url;
    private final String username;
    private final String password;


    public JdbcConnectionFactory(String driverClassName, String url, String username, String password) {
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
    public Connection openConnection() {
        try {
            return DriverManager.getConnection(url, username, password);
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }

    @Override
    public void closeConnection(Connection conn) {
        try {
            if (null != conn && !conn.isClosed()) {
                conn.close();
            }
        } catch (Exception ex){
            log.warn(ex.getMessage(), ex);
        }
    }
}
