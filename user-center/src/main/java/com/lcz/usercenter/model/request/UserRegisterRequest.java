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

    private String userName;

    private String userPassword;

    private String checkPassword;
}
