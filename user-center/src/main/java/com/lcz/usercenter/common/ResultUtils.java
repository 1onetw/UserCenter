package com.lcz.usercenter.common;

/**
 * 返回工具类
 * @author 1onetw
 * @version 1.0
 */
public class ResultUtils {

    public static <T> BaseResponse<T> success(T data) {
        return new BaseResponse<>(0, data, "success");
    }
}
