package com.github.giveme0101.service;

import com.github.giveme0101.entity.Order;

/**
 * @Author kevin xiajun94@FoxMail.com
 * @Description
 * @name IOrderService
 * @Date 2020/09/17 8:53
 */
public interface IOrderService {

    Order getOrderInfo(String orderCode);

}
