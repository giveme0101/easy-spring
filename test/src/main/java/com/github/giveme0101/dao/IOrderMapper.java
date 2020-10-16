package com.github.giveme0101.dao;

import com.github.giveme0101.entity.OrderDO;

import java.util.List;

public interface IOrderMapper {

    OrderDO get(String orderNo);

    List<OrderDO> selectAll();

    void insert(OrderDO orderDO);

    void update(OrderDO orderDO);

    void remove(String orderNo);

    boolean exist(String orderNo);

}
