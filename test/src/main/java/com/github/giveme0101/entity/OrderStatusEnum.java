package com.github.giveme0101.entity;

/**
 * @Author kevin xiajun94@FoxMail.com
 * @Description
 * @name OrderStatusEnum
 * @Date 2020/10/15 17:18
 */
public enum  OrderStatusEnum {

    CREATE("CREATE"),
    PAYED("PAYED"),
    COMPLETED("COMPLETED");

    OrderStatusEnum(String status) {
        this.status = status;
    }

    private String status;

    public String getStatus() {
        return status;
    }

    public static OrderStatusEnum of(String status){
        for (final OrderStatusEnum statusEnum : OrderStatusEnum.values()) {
            if (statusEnum.getStatus().equals(status)){
                return statusEnum;
            }
        }

        return null;
    }
}
