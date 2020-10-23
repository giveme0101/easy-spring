package com.github.giveme0101.controller;

import com.github.giveme0101.entity.Order;
import com.github.giveme0101.service.IOrderService;
import com.github.giveme0101.util.R;
import lombok.AllArgsConstructor;
import org.spring.framework.web.annotation.*;

/**
 * @Author kevin xiajun94@FoxMail.com
 * @Description
 * @name OrderController
 * @Date 2020/10/23 11:27
 */
@RestController
@AllArgsConstructor
public class OrderController {

    private IOrderService orderService;

    @GetMapping(value = "/api/order/{orderCode}")
    public R<Order> getOrder(@PathVariable("orderCode") String orderCode){
        return R.ok(orderService.getOrderInfo(orderCode));
    }

    @GetMapping(value = "/api/order")
    public R<Order> getOrder1(@RequestParam("orderCode") String orderCode){
        return R.ok(orderService.getOrderInfo(orderCode));
    }

    @PostMapping(value = "/api/order")
    public R<Boolean> saveOrder(@RequestBody Order order){
        orderService.save(order);
        return R.ok(true);
    }

}
