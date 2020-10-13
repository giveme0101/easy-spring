package com.github.giveme0101;

import com.github.giveme0101.entity.Order;
import com.github.giveme0101.service.IOrderService;
import com.github.giveme0101.service.impl.OrderServiceImpl;
import org.spring.framework.core.ComponentScan;
import org.spring.framework.core.context.AnnotationConfigApplicationContext;
import org.spring.framework.core.context.ApplicationContext;

/**
 * @Author kevin xiajun94@FoxMail.com
 * @Description
 * @name demo
 * @Date 2020/09/17 8:53
 */
@ComponentScan(basePackage = {
        "com.github.giveme0101.config",
        "com.github.giveme0101.dao",
        "com.github.giveme0101.service"
})
public class Demo {

    public static void main(String[] args) {

        ApplicationContext context = new AnnotationConfigApplicationContext(Demo.class);

//        IOrderService orderService = context.getBean("orderServiceImpl");

        IOrderService orderService = context.getBean(OrderServiceImpl.class);

        Order orderInfo = orderService.getOrderInfo("T-001");

        System.out.println(orderInfo);

    }

}
