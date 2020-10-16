package org.spring.framework.jdbc.tm;

import org.spring.framework.core.util.ContextLoader;
import org.spring.framework.jdbc.PooledConnectionFactory;

import java.util.Random;

/**
 * @Author kevin xiajun94@FoxMail.com
 * @Description
 * @name TransactionManager
 * @Date 2020/10/16 14:27
 */
public class TransactionManager {

    private PooledConnectionFactory connectionFactory;

    private static ThreadLocal<Long> TRANS_HOLDER = new ThreadLocal<>();

    public void openTransaction(){
        if (null == TRANS_HOLDER.get()){
            Long tsid = new Random().nextLong();
            TRANS_HOLDER.set(Math.abs(tsid));
            getConnectionFactory().openConnection();
        }
    }

    public void closeTransaction(){
        getConnectionFactory().closeConnection();
        TRANS_HOLDER.remove();
    }

    public void commit(){
        getConnectionFactory().commit();
    }

    public void rollback(){
        getConnectionFactory().rollback();
    }

    public boolean hasTransaction(){
        return getTid() != null;
    }

    public Long getTid(){
        return TRANS_HOLDER.get();
    }

    private PooledConnectionFactory getConnectionFactory(){
        if (null == connectionFactory){
            connectionFactory = ContextLoader.getContext().getBean(PooledConnectionFactory.class);
        }

        return connectionFactory;
    }

}
