package com.github.giveme0101.dao;

import com.github.giveme0101.entity.OrderDO;
import org.spring.framework.core.Component;

import java.util.Date;

/**
 * @Author kevin xiajun94@FoxMail.com
 * @Description
 * @name OrderMapper
 * @Date 2020/09/17 8:54
 */
@Component
public class OrderMapper {

    public OrderDO get(String orderNo){
        OrderDO orderDO = new OrderDO();
        orderDO.setOrderNo(orderNo);
        orderDO.setAmount(10.5);
        orderDO.setBuyerId("buyerId");
        orderDO.setSellerId("sellerId");
        orderDO.setCreateTime(new Date());
        return orderDO;
    }

}
