package com.github.giveme0101.dao;

import com.github.giveme0101.config.database.ConnectionFactory;
import com.github.giveme0101.config.database.SqlTemplate;
import com.github.giveme0101.entity.OrderDO;
import lombok.AllArgsConstructor;
import org.spring.framework.core.Component;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * @Author kevin xiajun94@FoxMail.com
 * @Description
 * @name OrderMapper
 * @Date 2020/09/17 8:54
 */
@Component
@AllArgsConstructor
public class OrderMapper extends SqlTemplate<OrderDO> {

//    @Autowired
    private ConnectionFactory connectionFactory;

    public OrderDO get(String orderNo){
        Connection conn = connectionFactory.getConnection();
        return selectOne(conn, "select * from `order` where order_no = ? ", new Object[]{orderNo});
    }

    public List<OrderDO> selectAll(){
        Connection conn = connectionFactory.getConnection();
        return select(conn, "select * from `order`", null);
    }

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
