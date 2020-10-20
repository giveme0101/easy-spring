package com.github.giveme0101.dao.impl;

import com.github.giveme0101.converter.OrderConverter;
import com.github.giveme0101.dao.IOrderMapper;
import com.github.giveme0101.entity.OrderDO;
import lombok.AllArgsConstructor;
import org.spring.framework.core.annotation.Component;
import org.spring.framework.jdbc.JdbcTemplate;

import java.util.List;

/**
 * @Author kevin xiajun94@FoxMail.com
 * @Description
 * @name OrderMapper
 * @Date 2020/09/17 8:54
 */
@Component
@AllArgsConstructor
public class OrderMapper implements IOrderMapper {

    private OrderConverter converter;
    private JdbcTemplate jdbcTemplate;

    @Override
    public OrderDO get(String orderNo){
        return jdbcTemplate.selectOne("select * from `order` where order_no = ? ", new Object[]{orderNo}, converter);
    }

    @Override
    public List<OrderDO> selectAll(){
        return jdbcTemplate.select("select * from `order`", null, converter);
    }

    @Override
    public void insert(OrderDO orderDO){
        jdbcTemplate.insert("insert into `order` (order_no, buyer_id, seller_id , amount, create_time) values (?, ?, ?, ?, ?) ", new Object[]{
                orderDO.getOrderNo(), orderDO.getBuyerId(), orderDO.getSellerId(), orderDO.getAmount(), orderDO.getCreateTime()
        });
    }

    @Override
    public void update(OrderDO orderDO){
        jdbcTemplate.update("update `order` set status = ? where order_no = ? ", new Object[]{
                orderDO.getStatus(), orderDO.getOrderNo()
        });
    }

    @Override
    public void remove(String orderNo){
        jdbcTemplate.delete("delete from `order` where order_no = ? ", new Object[]{orderNo});
    }

    @Override
    public boolean exist(String orderNo) {

//        List<Map<String, Object>> mapList = jdbcTemplate.selectMap("select count(*) as oc from `order` where order_no = ? limit 1 ", new Object[]{orderNo});
//        return (Long)mapList.get(0).get("oc") > 0;

        int count = jdbcTemplate.selectOne("select count(*) as oc from `order` where order_no = ? limit 1 ", new Object[]{orderNo}, rs -> rs.getInt("oc"));
        return count > 0;
    }

}
