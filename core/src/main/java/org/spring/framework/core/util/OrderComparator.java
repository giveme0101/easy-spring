package org.spring.framework.core.util;

import org.spring.framework.core.Ordered;
import org.spring.framework.core.PriorityOrdered;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * @Author kevin xiajun94@FoxMail.com
 * @Description
 * @name OrderComparator
 * @Date 2020/10/22 10:59
 */
public class OrderComparator implements Comparator<Object> {

    public static OrderComparator INSTANCE = new OrderComparator();

    @Override
    public int compare(Object o1, Object o2) {
        boolean p1 = (o1 instanceof PriorityOrdered);
        boolean p2 = (o2 instanceof PriorityOrdered);
        if (p1 && !p2) {
            return -1;
        }
        else if (p2 && !p1) {
            return 1;
        }

        int i1 = getOrder(o1);
        int i2 = getOrder(o2);
        return (i1 < i2) ? -1 : (i1 > i2) ? 1 : 0;
    }

    protected int getOrder(Object obj) {
        return (obj instanceof PriorityOrdered ? ((PriorityOrdered) obj).getOrder() : Ordered.LOWEST_PRECEDENCE);
    }

    public static void sort(List<?> list) {
        if (list.size() > 1) {
            Collections.sort(list, INSTANCE);
        }
    }

}
