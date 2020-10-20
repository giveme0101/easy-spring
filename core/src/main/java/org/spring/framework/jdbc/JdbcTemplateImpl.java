package org.spring.framework.jdbc;

import lombok.AllArgsConstructor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Author kevin xiajun94@FoxMail.com
 * @Description
 * @name JdbcTemplateImpl
 * @Date 2020/10/14 11:17
 */
@AllArgsConstructor
public class JdbcTemplateImpl implements JdbcTemplate {

    private ConnectionFactory connectionFactory;

    @Override
    public <DO> DO selectOne(String sql, Object[] args, ResultSetConverter<DO> converter){
        List<DO> list = select(sql, args, converter);
        return null == list || list.isEmpty() ? null : list.get(0);
    }

    @Override
    public <DO> List<DO> select(String sql, Object[] args, ResultSetConverter<DO> converter){

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {

            conn = connectionFactory.openConnection();
            pstmt = conn.prepareStatement(sql);
            if (null != args && args.length > 0){
                for (int i = 0; i < args.length; i++) {
                    pstmt.setObject(i + 1, args[i]);
                }
            }

            rs = pstmt.executeQuery();

            List<DO> list = new ArrayList<>();
            while (rs.next()) {
                list.add(converter.mapToDO(rs));
            }

            return list;
        } catch (Exception ex){
            throw new RuntimeException(ex);
        } finally {
            try {
                connectionFactory.closeConnection(conn);
                if (null != rs && !rs.isClosed()) rs.close();
                if (null != pstmt && !pstmt.isClosed()) pstmt.close();
            } catch (Exception ex) {}
        }
    }

    @Override
    public List<Map<String, Object>> selectMap(String sql, Object[] args) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {

            conn = connectionFactory.openConnection();
            pstmt = conn.prepareStatement(sql);
            if (null != args && args.length > 0){
                for (int i = 0; i < args.length; i++) {
                    pstmt.setObject(i + 1, args[i]);
                }
            }

            rs = pstmt.executeQuery();
            List<Map<String, Object>> list = new ArrayList<>();
            while (rs.next()) {
                list.add(DefaultResultSetConverter.INSTANCE.toMap(rs));
            }

            return list;
        } catch (Exception ex){
            throw new RuntimeException(ex);
        } finally {
            try {
                connectionFactory.closeConnection(conn);
                if (null != rs && !rs.isClosed()) rs.close();
                if (null != pstmt && !pstmt.isClosed()) pstmt.close();
            } catch (Exception ex) {}
        }
    }

    @Override
    public int insert(String sql, Object[] args){
        return execute(sql, args);
    }

    @Override
    public int update(String sql, Object[] args){
        return execute(sql, args);
    }

    @Override
    public int delete(String sql, Object[] args){
        return execute(sql, args);
    }

    private int execute(String sql, Object[] args){

        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = connectionFactory.openConnection();
            pstmt = conn.prepareStatement(sql);
            if (null != args && args.length > 0){
                for (int i = 0; i < args.length; i++) {
                    pstmt.setObject(i + 1, args[i]);
                }
            }
            int effectRows = pstmt.executeUpdate();
            return effectRows;
        } catch (Exception ex){
            throw new RuntimeException(ex);
        } finally {
            try {
                connectionFactory.closeConnection(conn);
                if (null != pstmt && !pstmt.isClosed()) pstmt.close();
            } catch (Exception ex) {}
        }
    }

    public static class DefaultResultSetConverter implements ResultSetConverter {

        public static ResultSetConverter INSTANCE = new DefaultResultSetConverter();

        @Override
        public Object mapToDO(ResultSet rs) {
            return null;
        }

    }

}
