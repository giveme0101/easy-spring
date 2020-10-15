package com.github.giveme0101.dao;

import com.github.giveme0101.converter.OrderConverter;
import com.github.giveme0101.config.database.ConnectionFactory;
import com.github.giveme0101.config.database.SqlTemplate;
import com.github.giveme0101.entity.OrderDO;
import lombok.AllArgsConstructor;
import org.spring.framework.core.Component;

import java.sql.Connection;
import java.util.List;

/**
 * @Author kevin xiajun94@FoxMail.com
 * @Description
 * @name OrderMapper
 * @Date 2020/09/17 8:54
 */
@Component
@AllArgsConstructor
public class OrderMapper {

    private OrderConverter converter;
    private ConnectionFactory connectionFactory;

    public OrderDO get(String orderNo){
        Connection conn = connectionFactory.getConnection();
        return SqlTemplate.selectOne(conn, "select * from `order` where order_no = ? ", new Object[]{orderNo}, converter);
    }

    public List<OrderDO> selectAll(){
        Connection conn = connectionFactory.getConnection();
        return SqlTemplate.select(conn, "select * from `order`", null, converter);
    }

}
