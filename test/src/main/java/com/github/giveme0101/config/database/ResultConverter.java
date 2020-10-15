package com.github.giveme0101.config.database;

import java.sql.ResultSet;
import java.sql.SQLException;

@FunctionalInterface
public interface ResultConverter {
    <T> T map(ResultSet rs) throws SQLException;
}