package com.lcz.usercenter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lcz.usercenter.common.ErrorCode;
import com.lcz.usercenter.exception.BusinessException;
import com.lcz.usercenter.model.domain.User;
import com.lcz.usercenter.service.UserService;
import com.lcz.usercenter.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.lcz.usercenter.constant.UserConstant.*;

/**
* @author l1853
* @description 针对表【user】的数据库操作Service实现
* @createDate 2025-06-20 05:11:17
*/
@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService{

    @Resource
    private UserMapper userMapper;

    @Override
    public Long userRegister(String userAccount, String userPassword, String checkPassword, String planetCode) {
        // 1.校验
        if (StringUtils.isAnyBlank(userAccount,userPassword,checkPassword, planetCode)){
            throw new BusinessException(ErrorCode.NULL_ERROR, "参数为空");
        }
        if (userAccount.length() < 4){
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "账户长度小于4位");
        }
        if (userPassword.length() < 8 || checkPassword.length() < 8){
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "密码长度小于8位");
        }
        if (planetCode.length() > 5){
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "星球编号大于5位");
        }

        // 账户不包含特殊字符
        Pattern pattern = Pattern.compile("[!@#$%^&*(){}\\[\\]:;\"',.<>?/~`]");
        Matcher matcher = pattern.matcher(userAccount);
        if (matcher.find()){
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "账户包含特殊字符");
        }

        // 校验密码是否相同
        if (!checkPassword.equals(userPassword)){
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "两次密码输入不一致");
        }

        // 账户不能重复
        QueryWrapper <User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userAccount",userAccount);
        long count = this.count(queryWrapper);
        if (count > 0){
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "账户重复");
        }

        // 星球编号不能重复
        queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("planetCode",planetCode);
        count = this.count(queryWrapper);
        if (count > 0){
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "星球编号重复");
        }

        // 2.加密 —— 先做一个测试【在项目测试类中测局部代码】
        String encyptPassword = DigestUtils.md5DigestAsHex((SALT + userPassword).getBytes());

        // 3.插入数据
        User user = new User();
        user.setUserAccount(userAccount);
        user.setUserPassword(encyptPassword);
        user.setPlanetCode(planetCode);
        boolean saveResult = this.save(user);
        if (!saveResult){
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "用户注册失败");
        }
        return user.getId();
    }

    @Override
    public User userLogin(String userAccount, String userPassword, HttpServletRequest request) {
        /* 1.校验 */
        if (StringUtils.isAnyBlank(userAccount,userPassword)){
            throw new BusinessException(ErrorCode.NULL_ERROR, "参数为空");
        }
        if (userAccount.length() < 4){
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "账户长度小于4位");
        }
        if (userPassword.length() < 8){
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "密码长度小于8位");
        }

        // 账户不包含特殊字符
        Pattern pattern = Pattern.compile("[!@#$%^&*(){}\\[\\]:;\"',.<>?/~`]");
        Matcher matcher = pattern.matcher(userAccount);
        if (matcher.find()){
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "账户包含特殊字符");
        }

        /* 2.查询用户是否存在 */
        // 加密
        String encyptPassword = DigestUtils.md5DigestAsHex((SALT + userPassword).getBytes());
        // 查询用户是否存在
        QueryWrapper<User> queryWrapper = new QueryWrapper<User>();
        queryWrapper.eq("userAccount", userAccount);
        queryWrapper.eq("userPassword", encyptPassword);
        User user = userMapper.selectOne(queryWrapper);
        // 用户不存在
        if (user == null){
            log.info("user login failed, userPassword is not correct.");
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "用户不存在");
        }

        /* 3.用户脱敏 */
        User safetyUser = getSafetyUser(user);

        /* 4.记录用户登录态（session）*/
        request.getSession().setAttribute(USER_LOGIN_STATE, safetyUser);

        return safetyUser;
    }

    @Override
    public boolean isAdmin(HttpServletRequest request) {
        User safetyUser = (User) request.getSession().getAttribute(USER_LOGIN_STATE);
        if (safetyUser == null || safetyUser.getUserRole() == ADMIN_ROLE){
            return false;
        }
        return true;
    }

    @Override
    public User getSafetyUser(User originUser) {
        User safetyUser = new User();
        safetyUser.setId(originUser.getId());
        safetyUser.setUsername(originUser.getUsername());
        safetyUser.setUserAccount(originUser.getUserAccount());
        safetyUser.setAvatarUrl(originUser.getAvatarUrl());
        safetyUser.setGender(originUser.getGender());
        safetyUser.setPhone(originUser.getPhone());
        safetyUser.setEmail(originUser.getEmail());
        safetyUser.setUserStatus(originUser.getUserStatus());
        safetyUser.setCreateTime(originUser.getCreateTime());
        safetyUser.setUserRole(originUser.getUserRole());
        safetyUser.setPlanetCode(originUser.getPlanetCode());
        return safetyUser;
    }

    @Override
    public Integer userLogout(HttpServletRequest request) {
        request.getSession().removeAttribute(USER_LOGIN_STATE);
        return 1;
    }
}




