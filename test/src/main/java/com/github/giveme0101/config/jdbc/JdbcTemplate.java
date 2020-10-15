package com.github.giveme0101.config.jdbc;

import java.util.List;

public interface JdbcTemplate {

    <DO> DO selectOne(String sql, Object[] args, ResultSetConverter<DO> converter);

    <DO> List<DO> select(String sql, Object[] args, ResultSetConverter<DO> converter);

    int insert(String sql, Object[] args);

    int update(String sql, Object[] args);

    int delete(String sql, Object[] args);
}
