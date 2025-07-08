package com.lcz.usercenter.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lcz.usercenter.common.BaseResponse;
import com.lcz.usercenter.common.ErrorCode;
import com.lcz.usercenter.common.ResultUtils;
import com.lcz.usercenter.model.domain.User;
import com.lcz.usercenter.model.request.UserLoginRequest;
import com.lcz.usercenter.model.request.UserRegisterRequest;
import com.lcz.usercenter.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author lcz
 * @version 2.0
 */
@RestController
@RequestMapping("/api/user")
@CrossOrigin(origins = {"http://localhost:3000"}, allowCredentials = "true")
public class UserController {
    @Resource
    private UserService userService;

    @PostMapping("register")
    public BaseResponse<Long> userRegister(@RequestBody UserRegisterRequest userRegisterRequest) {
        if (userRegisterRequest == null) {
            return ResultUtils.error(ErrorCode.NULL_ERROR);
        }
        String userName = userRegisterRequest.getUserName();
        String userPassword = userRegisterRequest.getUserPassword();
        String checkPassword = userRegisterRequest.getCheckPassword();
        String planetCode = userRegisterRequest.getPlanetCode();
        if (StringUtils.isAnyBlank(userName, userPassword, checkPassword, planetCode)) {
            return ResultUtils.error(ErrorCode.NULL_ERROR);
        }
        return userService.userRegister(userName, userPassword, checkPassword, planetCode);
    }

    @PostMapping("login")
    public BaseResponse<User> userLogin(@RequestBody UserLoginRequest userLoginRequest, HttpServletRequest request) {
        if (userLoginRequest == null) {
            return ResultUtils.error(ErrorCode.NULL_ERROR);
        }
        String userName = userLoginRequest.getUserName();
        String userPassword = userLoginRequest.getUserPassword();
        if (StringUtils.isAnyBlank(userName, userPassword)) {
            return ResultUtils.error(ErrorCode.NULL_ERROR);
        }
        return userService.userLogin(userName, userPassword, request);
    }

    @GetMapping("search")
    public BaseResponse<List<User>> searchUsers(String userName, HttpServletRequest request) {
        /* 1.鉴权 */
        if (!userService.isAdmin(request)) {
            return ResultUtils.error(ErrorCode.NO_AUTH);
        }
        /* 2.查询用户列表 */
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        // 用户名不为空
        if (StringUtils.isNotBlank(userName)) {
            queryWrapper.like(true, "username", userName);
        }
        List<User> userList = userService.list(queryWrapper);
        List<User> result = userList.stream().map(user ->
        {
            // 用户脱敏
            return userService.getSafetyUser(user);
        }).collect(Collectors.toList());
        return ResultUtils.success(result);
    }

    @PostMapping("delete")
    public BaseResponse<Boolean> deleteUser(@RequestBody long id, HttpServletRequest request) {
        /* 1.鉴权 */
        if (!userService.isAdmin(request)) {
            return ResultUtils.error(ErrorCode.NO_AUTH);
        }
        /* 2.删除用户 */
        if (id <= 0) {
            return ResultUtils.error(ErrorCode.PARAMS_ERROR);
        }
        boolean result = userService.removeById(id);
        return ResultUtils.success(result);
    }

    @PostMapping("logout")
    public BaseResponse<Integer> userLogout(HttpServletRequest request) {
        return userService.userLogout(request);
    }

}
