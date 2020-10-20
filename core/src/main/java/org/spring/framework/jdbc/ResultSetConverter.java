package org.spring.framework.jdbc;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@FunctionalInterface
public interface ResultSetConverter<DO> {

    DO mapToDO(ResultSet rs) throws SQLException;

    default Map<String, Object> toMap(ResultSet rs) throws SQLException {

        Map<String, Object> result = new HashMap();

        ResultSetMetaData metaData = rs.getMetaData();
        int columnCount = metaData.getColumnCount();

        for (int i = 1; i <= columnCount; i++) {
            result.put(metaData.getColumnName(i), rs.getObject(i));
        }

        return result;
    }

}