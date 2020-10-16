package com.github.giveme0101.converter;

import com.github.convert.BeanConverter;
import org.spring.framework.jdbc.ResultSetConverter;
import com.github.giveme0101.entity.Order;
import com.github.giveme0101.entity.OrderDO;
import com.github.giveme0101.entity.OrderStatusEnum;
import org.spring.framework.core.annotation.Autowired;
import org.spring.framework.core.annotation.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @Author kevin xiajun94@FoxMail.com
 * @Description
 * @name OrderConverter
 * @Date 2020/10/15 14:14
 */
@Component
public class OrderConverter implements ResultSetConverter<OrderDO>, TwoWayConverter<Order, OrderDO> {

    @Autowired
    private BeanConverter beanConverter;

    @Override
    public OrderDO mapToDO(ResultSet rs) throws SQLException {
        OrderDO orderDO = new OrderDO();
        orderDO.setId(rs.getInt("id"));
        orderDO.setOrderNo(rs.getString("order_no"));
        orderDO.setSellerId(rs.getString("seller_id"));
        orderDO.setBuyerId(rs.getString("buyer_id"));
        orderDO.setAmount(rs.getDouble("amount"));
        orderDO.setStatus(rs.getString("status"));
        orderDO.setCreateTime(rs.getDate("create_time"));
        return orderDO;
    }

    @Override
    public Order convertToBO(OrderDO orderDO) {
        Order order = Order.builder()
                .status(OrderStatusEnum.of(orderDO.getStatus()))
                .build();
        beanConverter.convert(orderDO, order);
        return order;
    }

    @Override
    public OrderDO convertToDO(Order order) {
        return OrderDO.builder()
                .orderNo(order.getOrderNo())
                .buyerId(order.getBuyerId())
                .sellerId(order.getSellerId())
                .amount(order.getAmount())
                .status(order.getStatus().getStatus())
                .createTime(order.getCreateTime())
                .build();
    }
}
