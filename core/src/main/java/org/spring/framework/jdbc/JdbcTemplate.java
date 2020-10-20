package org.spring.framework.jdbc;

import java.util.List;
import java.util.Map;

public interface JdbcTemplate {

    <DO> DO selectOne(String sql, Object[] args, ResultSetConverter<DO> converter);

    <DO> List<DO> select(String sql, Object[] args, ResultSetConverter<DO> converter);

    List<Map<String, Object>> selectMap(String sql, Object[] args);

    int insert(String sql, Object[] args);

    int update(String sql, Object[] args);

    int delete(String sql, Object[] args);

}
