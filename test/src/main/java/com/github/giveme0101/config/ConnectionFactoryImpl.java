package com.github.giveme0101.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @Author kevin xiajun94@FoxMail.com
 * @Description
 * @name ConnectionFactory
 * @Date 2020/10/14 11:12
 */
public class ConnectionFactoryImpl implements ConnectionFactory {

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Connection getConnection(){
        try {
            return DriverManager.getConnection("jdbc:mysql://localhost:3306/demo?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC", "root", "root");
        } catch (SQLException exception) {
           throw new RuntimeException(exception);
        }
    }

}
