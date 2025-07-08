package com.lcz.usercenter.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
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
 * @version 1.0
 */
@RestController
@RequestMapping("/api/user")
@CrossOrigin(origins = {"http://localhost:3000"}, allowCredentials = "true")
public class UserController {
    @Resource
    private UserService userService;

    @PostMapping("register")
    public Long userRegister(@RequestBody UserRegisterRequest userRegisterRequest) {
        if (userRegisterRequest == null) {
            return null;
        }
        String userName = userRegisterRequest.getUserName();
        String userPassword = userRegisterRequest.getUserPassword();
        String checkPassword = userRegisterRequest.getCheckPassword();
        if (StringUtils.isAnyBlank(userName, userPassword, checkPassword)) {
            return null;
        }
        return userService.userRegister(userName, userPassword, checkPassword);
    }

    @PostMapping("login")
    public User userLogin(@RequestBody UserLoginRequest userLoginRequest, HttpServletRequest request) {
        if (userLoginRequest == null) {
            return null;
        }
        String userName = userLoginRequest.getUserName();
        String userPassword = userLoginRequest.getUserPassword();
        if (StringUtils.isAnyBlank(userName, userPassword)) {
            return null;
        }
        return userService.userLogin(userName, userPassword, request);
    }

    @GetMapping("search")
    public List<User> searchUsers(String userName, HttpServletRequest request) {
        /* 1.鉴权 */
        if (!userService.isAdmin(request)) {
            return new ArrayList<>();
        }
        /* 2.查询用户列表 */
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        // 用户名不为空
        if (StringUtils.isNotBlank(userName)) {
            queryWrapper.like(true, "username", userName);
        }
        List<User> userList = userService.list(queryWrapper);
        return userList.stream().map(user ->
        {
            // 用户脱敏
            return userService.getSafetyUser(user);
        }).collect(Collectors.toList());
    }

    @PostMapping("delete")
    public boolean deleteUser(@RequestBody long id, HttpServletRequest request) {
        /* 1.鉴权 */
        if (!userService.isAdmin(request)) {
            return false;
        }
        /* 2.删除用户 */
        if (id <= 0) {
            return false;
        }
        return userService.removeById(id);
    }

    @PostMapping("logout")
    public Integer userLogout(HttpServletRequest request) {
        return userService.userLogout(request);
    }

}
