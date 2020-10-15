package com.github.giveme0101.service;

import com.github.giveme0101.entity.Order;

import java.util.List;

/**
 * @Author kevin xiajun94@FoxMail.com
 * @Description
 * @name IOrderService
 * @Date 2020/09/17 8:53
 */
public interface IOrderService {

    Order getOrderInfo(String orderCode);

    List<Order> getOrderList();

    void save(Order order);

    void remove(String orderCode);

    void setPayed(String orderCode);

}
