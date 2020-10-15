package com.github.giveme0101.config.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author kevin xiajun94@FoxMail.com
 * @Description
 * @name SqlTemplate
 * @Date 2020/10/14 11:17
 */
public class SqlTemplate {

    public static <T> T selectOne(Connection conn, String sql, Object[] args, ResultConverter converter){
        List<T> list = select(conn, sql, args, converter);
        return list.get(0);
    }

    public static <T> List<T> select(Connection conn, String sql, Object[] args, ResultConverter converter){

        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            pstmt = conn.prepareStatement(sql);
            if (null != args && args.length > 0){
                for (int i = 0; i < args.length; i++) {
                    pstmt.setObject(i + 1, args[i]);
                }
            }

            rs = pstmt.executeQuery();

            List<T> list = new ArrayList<>();
            while (rs.next()) {
                list.add(converter.map(rs));
            }

            return list;
        } catch (Exception ex){
            throw new RuntimeException(ex);
        } finally {
            try {
                if (null != rs && !rs.isClosed()) rs.close();
                if (null != pstmt && !pstmt.isClosed()) pstmt.close();
                if (null != conn && !conn.isClosed()) conn.close();
            } catch (Exception ex) {}
        }
    }

}
