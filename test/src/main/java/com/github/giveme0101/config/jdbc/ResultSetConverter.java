package com.github.giveme0101.config.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;

@FunctionalInterface
public interface ResultSetConverter<DO> {

    DO mapToDO(ResultSet rs) throws SQLException;

}