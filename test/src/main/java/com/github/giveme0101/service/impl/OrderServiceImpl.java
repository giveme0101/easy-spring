package com.github.giveme0101.service.impl;

import com.github.giveme0101.dao.OrderMapper;
import com.github.giveme0101.entity.Order;
import com.github.giveme0101.entity.OrderDO;
import com.github.giveme0101.service.IOrderService;
import lombok.extern.slf4j.Slf4j;
import org.spring.framework.core.Autowired;
import org.spring.framework.core.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author kevin xiajun94@FoxMail.com
 * @Description
 * @name OrderServiceImpl
 * @Date 2020/09/17 8:53
 */
@Slf4j
@Service
public class OrderServiceImpl implements IOrderService {

    @Autowired
    private OrderMapper orderMapper;

    @Override
    public Order getOrderInfo(String orderCode) {
        log.info("getOrderInfo: {}", orderCode);
        OrderDO orderDO = orderMapper.get(orderCode);
        return orderDO == null ? null : Order.builder()
                .orderNo(orderDO.getOrderNo())
                .buyerId(orderDO.getBuyerId())
                .sellerId(orderDO.getSellerId())
                .amount(orderDO.getAmount())
                .createTime(orderDO.getCreateTime())
                .build();
    }

    @Override
    public List<Order> getOrderList() {
        log.info("getOrderList");
        List<OrderDO> orderList = orderMapper.selectAll();
        if (null == orderList){
            return null;
        }

        return orderList.stream().map(orderDO -> Order.builder()
                .orderNo(orderDO.getOrderNo())
                .buyerId(orderDO.getBuyerId())
                .sellerId(orderDO.getSellerId())
                .amount(orderDO.getAmount())
                .createTime(orderDO.getCreateTime())
                .build()
        ).collect(Collectors.toList());
    }
}
