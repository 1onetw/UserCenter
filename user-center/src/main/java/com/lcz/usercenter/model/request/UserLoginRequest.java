package com.lcz.usercenter.model.request;

import lombok.Data;

import java.io.Serializable;

/**
 * @author lcz
 * @version 1.0
 * 用户登录请求体
 */
@Data
public class UserLoginRequest implements Serializable {

    private String userName;

    private String userPassword;
}
