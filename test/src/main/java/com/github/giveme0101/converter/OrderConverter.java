package com.github.giveme0101.converter;

import com.github.giveme0101.config.database.ResultConverter;
import com.github.giveme0101.entity.OrderDO;
import org.spring.framework.core.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @Author kevin xiajun94@FoxMail.com
 * @Description
 * @name OrderConverter
 * @Date 2020/10/15 14:14
 */
@Component
public class OrderConverter implements ResultConverter {

    @Override
    public OrderDO map(ResultSet rs) throws SQLException {
        OrderDO orderDO = new OrderDO();
        orderDO.setId(rs.getInt("id"));
        orderDO.setOrderNo(rs.getString("order_no"));
        orderDO.setSellerId(rs.getString("seller_id"));
        orderDO.setBuyerId(rs.getString("buyer_id"));
        orderDO.setAmount(rs.getDouble("amount"));
        orderDO.setCreateTime(rs.getDate("create_time"));
        return orderDO;
    }

}
