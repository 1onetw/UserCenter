package com.lcz.usercenter.common;

import lombok.Getter;

/**
 * 状态码
* @author 1onetw
* @version 1.0
*/
@Getter
public enum ErrorCode {
    SUCCESS(0,"success",""),
    PARAMS_ERROR(40001,"请求参数错误",""),
    NULL_ERROR(40002,"请求数据为空",""),
    NO_LOGIN(40100,"未登录",""),
    NO_AUTH(40101,"没有权限",""),
    SYSTEM_ERROR(50000,"系统内部异常","");

    /**
     * 状态码
     */
    private final int code;

    /**
     * 状态码信息
     */
    private final String message;

    /**
     * 状态码描述（详情）
     */
    private final String description;

    ErrorCode(int code, String message, String description) {
        this.code = code;
        this.message = message;
        this.description = description;
    }

}
