package org.spring.framework.core.util;

import org.spring.framework.core.Ordered;
import org.spring.framework.core.annotation.Order;

/**
 * @Author kevin xiajun94@FoxMail.com
 * @Description
 * @name AnnotationAwareOrderComparator
 * @Date 2020/10/22 11:10
 */
public class AnnotationAwareOrderComparator extends OrderComparator {

    @Override
    protected int getOrder(Object obj) {
        if (obj instanceof Ordered) {
            return ((Ordered) obj).getOrder();
        }
        if (obj != null) {
            Order order = obj.getClass().getAnnotation(Order.class);
            if (order != null) {
                return order.value();
            }
        }
        return Ordered.LOWEST_PRECEDENCE;
    }

}
