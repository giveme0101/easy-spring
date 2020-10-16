package org.spring.framework.jdbc;

import lombok.extern.slf4j.Slf4j;
import org.spring.framework.core.util.ContextLoader;
import org.spring.framework.jdbc.tm.TransactionManager;

import java.sql.Connection;

/**
 * @Author kevin xiajun94@FoxMail.com
 * @Description
 * @name JdbcConnectionFactory
 * @Date 2020/10/14 11:12
 */
@Slf4j
public class ProxyConnectionFactory implements PooledConnectionFactory {

    private ConnectionFactory target;
    private TransactionManager transactionManager;

    private static ThreadLocal<Connection> CONN_HOLDER = new ThreadLocal<>();

    public ProxyConnectionFactory(String driverClassName, String url, String username, String password) {
        target = new JdbcConnectionFactory(driverClassName, url, username, password);
        transactionManager = ContextLoader.getContext().getBean(TransactionManager.class);
    }

    @Override
    public Connection openConnection() {
        Connection connection = CONN_HOLDER.get();
        if (null == connection){
            log.debug("get connection, tid = {}", getTid());
            connection = target.openConnection();
            CONN_HOLDER.set(connection);
        }

        if (null != transactionManager && transactionManager.hasTransaction()){
            try {
                connection.setAutoCommit(false);
            } catch (Exception ex){
                log.error(ex.getMessage(), ex);
            }
        }

        return connection;
    }

    @Override
    public void closeConnection(Connection conn) {
        if (null != conn){
            if (transactionManager != null && transactionManager.hasTransaction()){
                log.debug("return connection, tid = {}", getTid());
                return;
            } else {
                target.closeConnection(conn);
                CONN_HOLDER.remove();
                log.debug("close connection, tid = {}", getTid());
            }
        }
    }

    @Override
    public void closeConnection(){
        Connection connection = CONN_HOLDER.get();
        if (null != connection && null != target){
            target.closeConnection(connection);
            CONN_HOLDER.remove();
            log.debug("close connection, tid = {}", getTid());
        }
    }

    @Override
    public void commit() {
        Connection connection = CONN_HOLDER.get();
        if (null != connection){
            try {
                connection.commit();
                log.debug("commit connection, tid = {}", getTid());
            } catch (Exception ex){
                log.error(ex.getMessage(), ex);
            }
        }
    }

    @Override
    public void rollback() {
        Connection connection = CONN_HOLDER.get();
        if (null != connection){
            try {
                connection.rollback();
                log.debug("rollback connection, tid = {}", getTid());
            } catch (Exception ex){
                log.error(ex.getMessage(), ex);
            }
        }
    }

    private Integer getTid(){
        return transactionManager != null ? transactionManager.getTid() : null;
    }

}
