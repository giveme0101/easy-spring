package org.spring.framework.jdbc;

import java.sql.Connection;

/**
 * @Author kevin xiajun94@FoxMail.com
 * @Description
 * @name ConnectionFactory
 * @Date 2020/10/14 11:15
 */
public interface ConnectionFactory {

    Connection openConnection();

    void closeConnection(Connection conn);

}
