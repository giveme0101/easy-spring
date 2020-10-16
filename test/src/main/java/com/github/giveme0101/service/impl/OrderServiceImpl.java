package com.github.giveme0101.service.impl;

import com.github.giveme0101.converter.OrderConverter;
import com.github.giveme0101.dao.IOrderMapper;
import com.github.giveme0101.entity.Order;
import com.github.giveme0101.entity.OrderDO;
import com.github.giveme0101.entity.OrderStatusEnum;
import com.github.giveme0101.service.IOrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@RequiredArgsConstructor
public class OrderServiceImpl implements IOrderService {

    // FIXME 此处如果是IOrderMapper的实现类会报错
    private final IOrderMapper orderMapper;
    private final OrderConverter orderConverter;

    @Override
    public Order getOrderInfo(String orderCode) {
        OrderDO orderDO = orderMapper.get(orderCode);
        return orderDO == null ? null : orderConverter.convertToBO(orderDO);
    }

    @Override
    public List<Order> getOrderList() {
        List<OrderDO> orderList = orderMapper.selectAll();
        return orderList.stream().map(orderConverter::convertToBO).collect(Collectors.toList());
    }

    @Override
    public void save(Order order) {
        OrderDO orderDO = orderConverter.convertToDO(order);
        orderMapper.insert(orderDO);
    }

    @Override
    public void remove(String orderCode) {
        orderMapper.remove(orderCode);
    }

    @Override
    public void setPayed(String orderCode) {
        orderMapper.setStatus(OrderStatusEnum.PAYED, orderCode);
    }

}
