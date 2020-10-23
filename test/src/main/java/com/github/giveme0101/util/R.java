package com.github.giveme0101.util;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @Author kevin xiajun94@FoxMail.com
 * @Description
 * @name R
 * @Date 2020/10/23 11:28
 */
@Getter
@Setter
public class R<T> {

    private String code;
    private T data;
    private String message;

    public static final String SUCCESS_CODE = "200";
    public static final String ERROR_CODE = "400";

    public R(String code, T data, String message) {
        this.code = code;
        this.data = data;
        this.message = message;
    }

    public static <T> R ok(T data){
        return new R(SUCCESS_CODE, data, null);
    }

    public static <T> R ok(List<T> data){
        return new R(SUCCESS_CODE, data, null);
    }

    public static R error(String msg){
        return new R(ERROR_CODE, null, msg);
    }

}
