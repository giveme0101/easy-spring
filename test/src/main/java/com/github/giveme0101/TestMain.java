package com.github.giveme0101;

import com.github.convert.BeanConverter;
import com.github.giveme0101.entity.Order;
import com.github.giveme0101.service.IOrderService;
import org.spring.framework.core.ComponentScan;
import org.spring.framework.core.Import;
import org.spring.framework.core.context.AnnotationConfigApplicationContext;
import org.spring.framework.core.context.ApplicationContext;

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

        ApplicationContext context = new AnnotationConfigApplicationContext(TestMain.class);

        IOrderService orderService = context.getBean(IOrderService.class);
        Order orderInfo = orderService.getOrderInfo("O-00001");
        System.out.println(orderInfo);

        orderService.payOrder(orderInfo.getOrderNo());

        List<Order> orderList = orderService.getOrderList();
        orderList.stream().forEach(System.out::println);

    }

}
