package com.github.giveme0101.entity;

import lombok.Data;

import java.util.Date;

/**
 * @Author kevin xiajun94@FoxMail.com
 * @Description
 * @name Order
 * @Date 2020/09/17 8:54
 */
@Data
public class OrderDO {

    private Integer id;

    private String orderNo;

    private String buyerId;

    private String sellerId;

    private Double amount;

    private Date createTime;

}
