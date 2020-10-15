package com.github.giveme0101.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @Author kevin xiajun94@FoxMail.com
 * @Description
 * @name Order
 * @Date 2020/09/17 8:54
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Order {

    private String orderNo;

    private String buyerId;

    private String sellerId;

    private Double amount;

    private OrderStatusEnum status;

    private Date createTime;

}
