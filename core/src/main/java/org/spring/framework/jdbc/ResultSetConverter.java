package org.spring.framework.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;

@FunctionalInterface
public interface ResultSetConverter<DO> {

    DO mapToDO(ResultSet rs) throws SQLException;

}