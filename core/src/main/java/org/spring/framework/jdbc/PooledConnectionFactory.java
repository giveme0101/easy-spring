package org.spring.framework.jdbc;

/**
 * @Author kevin xiajun94@FoxMail.com
 * @Description
 * @name PooledConnectionFactory
 * @Date 2020/10/14 11:15
 */
public interface PooledConnectionFactory extends ConnectionFactory {

    void closeConnection();

    void commit();

    void rollback();

}
