package com.lcz.usercenter.common;

import lombok.Data;

import java.io.Serializable;

/**
 * 通用返回类
 * @author 1onetw
 * @version 1.0
 */
@Data
public class BaseResponse<T> implements Serializable {

    /**
     * 错误码
     */
    private int code;

    /**
     * 数据
     */
    private T data;

    /**
     * 信息
     */
    private String message;

    public BaseResponse(int code, T data, String message) {
        this.code = code;
        this.data = data;
        this.message = message;
    }

    public BaseResponse(int code, T data) {
        this.code = code;
        this.data = data;
    }
}
