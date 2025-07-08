package com.lcz.usercenter.model.request;

import lombok.Data;
import org.apache.ibatis.javassist.SerialVersionUID;

import java.io.Serializable;

/**
 * @author lcz
 * @version 1.0
 * 用户注册请求体
 */
@Data
public class UserRegisterRequest implements Serializable {

    /**
     * 用户名
     */
    private String userName;

    /**
     * 用户密码
     */
    private String userPassword;

    /**
     * 校验密码
     */
    private String checkPassword;

    /**
     * 星球编号
     */
    private String planetCode;
}
