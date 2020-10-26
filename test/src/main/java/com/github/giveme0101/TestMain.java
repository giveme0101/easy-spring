package com.github.giveme0101;

import com.github.convert.BeanConverter;
import com.github.giveme0101.entity.Order;
import com.github.giveme0101.entity.OrderStatusEnum;
import com.github.giveme0101.service.IOrderService;
import org.spring.framework.SpringApplication;
import org.spring.framework.core.annotation.ComponentScan;
import org.spring.framework.core.annotation.Import;
import org.spring.framework.core.context.ApplicationContext;

import java.util.Date;
import java.util.List;

/**
 * @Author kevin xiajun94@FoxMail.com
 * @Description
 * @name demo
 * @Date 2020/09/17 8:53
 */
@ComponentScan(basePackage = {
        "com.github.giveme0101.config",
        "com.github.giveme0101.converter",
        "com.github.giveme0101.dao",
        "com.github.giveme0101.service"
})
@Import(BeanConverter.class)
public class TestMain {

    public static void main(String[] args) {

        ApplicationContext context = SpringApplication.run(TestMain.class, args);

        IOrderService orderService = context.getBean(IOrderService.class);

        String orderNo = "O-000017";

        System.out.println("===========================================================================================================================");

        orderService.save(Order.builder()
                .orderNo(orderNo)
                .buyerId("buyer")
                .sellerId("seller")
                .amount(100.00)
                .createTime(new Date())
                .status(OrderStatusEnum.PAYED)
                .build());

        System.out.println("===========================================================================================================================");

        Order orderInfo = orderService.getOrderInfo(orderNo);
        System.out.println(orderInfo);

        System.out.println("===========================================================================================================================");

        List<Order> orderList = orderService.getOrderList();
        orderList.stream().forEach(System.out::println);

        System.out.println("===========================================================================================================================");

        orderService.delete(orderNo);

        System.out.println("===========================================================================================================================");

    }

}
