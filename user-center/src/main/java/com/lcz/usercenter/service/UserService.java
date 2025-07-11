package com.lcz.usercenter.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lcz.usercenter.model.domain.User;

import javax.servlet.http.HttpServletRequest;

/**
* @author l1853
* @description 针对表【user】的数据库操作Service
* @createDate 2025-06-20 05:11:17
*/
public interface UserService extends IService<User> {

    /**
     * 用户注册
     *
     * @param userAccount 用户账户
     * @param userPassword 用户密码
     * @param checkPassword 校验码
     * @param planetCode 星球编号
     * @return 新用户 id
     */
    Long userRegister(String userAccount, String userPassword, String checkPassword, String planetCode);

    /**
     * 用户登录
     * @param userAccount 用户账户
     * @param userPassword 用户密码
     * @param request HttpServletRequest对象
     * @return 脱敏后用户信息
     */
    User userLogin(String userAccount, String userPassword, HttpServletRequest request);

    /**
     * 判断是否为管理员
     * @param request HttpServletRequest对象
     * @return 布尔值
     */
    boolean isAdmin(HttpServletRequest request);

    /**
     * 获取脱敏用户
     * @param originUser 未脱敏用户
     * @return 脱敏用户
     */
    User getSafetyUser(User originUser);

    /**
     * 用户注销
     *
     * @param request HttpServletRequest对象
     * @return 1 注销成功
     */
    Integer userLogout(HttpServletRequest request);
}
