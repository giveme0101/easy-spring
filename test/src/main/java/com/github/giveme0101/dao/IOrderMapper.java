package com.github.giveme0101.dao;

import com.github.giveme0101.entity.OrderDO;
import com.github.giveme0101.entity.OrderStatusEnum;

import java.util.List;

public interface IOrderMapper {

    OrderDO get(String orderNo);

    List<OrderDO> selectAll();

    void insert(OrderDO orderDO);

    void remove(String orderNo);

    void setStatus(OrderStatusEnum orderStatusEnum, String orderNo);

}
