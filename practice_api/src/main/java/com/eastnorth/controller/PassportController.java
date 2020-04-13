package com.eastnorth.controller;

import com.eastnorth.pojo.Users;
import com.eastnorth.pojo.bo.UserBO;
import com.eastnorth.service.UserService;
import com.eastnorth.utils.CookieUtils;
import com.eastnorth.utils.JsonUtils;
import com.eastnorth.bean.ResponseBean;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author zuojianhou
 * @date   2020/4/8
 * @Description:
 */
@Api(value = "注册登录", tags = {"用于注册登录的相关接口"})
@RestController
@RequestMapping("/passport")
public class PassportController {

    @Autowired
    private UserService userService;

    @ApiOperation(value = "用户名是否存在", notes = "用户名是否存在", httpMethod = "GET")
    @GetMapping("/usernameIsExist")
    public HttpStatus usernameIsExist(@RequestParam String username) {

        // 1.判断用户名不能为空
        if (StringUtils.isBlank(username)) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }

        // 2.查找注册的用户名是否存在
        boolean isExist = userService.queryUsernameIsExist(username);
        if (isExist) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }

        // 3.请求成功, 用户名没有重复
        return HttpStatus.OK;
    }

    @ApiOperation(value = "用户注册", notes = "用户注册", httpMethod = "POST")
    @PostMapping("/regist")
    public ResponseBean register(@RequestBody UserBO userBO, HttpServletRequest request, HttpServletResponse response) {

        ResponseBean responseBean = new ResponseBean();

        String username = userBO.getUsername();
        String password = userBO.getPassword();
        String confirmPwd = userBO.getConfirmPassword();

        // 0.判断用户名和密码必须不为空
        if (StringUtils.isBlank(username) ||
                StringUtils.isBlank(password) ||
                StringUtils.isBlank(confirmPwd)) {
            responseBean.setResult(false);
            responseBean.setStatus(400);
            responseBean.setMsg("用户名或密码不能为空");
            responseBean.setData(userBO.toString());
            return responseBean;
        }
        // 1.查询用户名是否存在
        boolean isExist = userService.queryUsernameIsExist(username);
        if (isExist) {
            responseBean.setResult(false);
            responseBean.setStatus(401);
            responseBean.setMsg("用户名已经存在");
            responseBean.setData(userBO.toString());
            return responseBean;
        }
        // 2.密码长度不能少于6位
        if (password.length() < 6) {
            responseBean.setResult(false);
            responseBean.setStatus(402);
            responseBean.setMsg("密码长度不能小于6位");
            responseBean.setData(userBO.toString());
            return responseBean;
        }
        // 3.判断两次密码是否一致
        if (!password.equals(confirmPwd)) {
            responseBean.setResult(false);
            responseBean.setStatus(403);
            responseBean.setMsg("两次密码输入不一致");
            responseBean.setData(userBO.toString());
            return responseBean;
        }
        // 4.实现注册
        Users userResult = userService.createUser(userBO);

        userResult = this.setNullProperty(userResult);

        CookieUtils.setCookie(request, response, "user", JsonUtils.objectToJson(userResult), true);


        responseBean.setResult(true);
        responseBean.setStatus(200);
        responseBean.setMsg("用户创建成功");
        responseBean.setData(userBO.toString());
        return responseBean;
    }

    @ApiOperation(value = "用户登录", notes = "用户登录", httpMethod = "POST")
    @PostMapping("/login")
    public ResponseBean login(@RequestBody UserBO userBO, HttpServletRequest request, HttpServletResponse response) {

        ResponseBean responseBean = new ResponseBean();

        String username = userBO.getUsername();
        String password = userBO.getPassword();

        // 0.判断用户名和密码必须不为空
        if (StringUtils.isBlank(username) ||
                StringUtils.isBlank(password)) {
            responseBean.setResult(false);
            responseBean.setStatus(400);
            responseBean.setMsg("用户名或密码不能为空");
            responseBean.setData(userBO.toString());
            return responseBean;
        }

        // 1.实现登录
        Users userResult = userService.queryUserForLogin(username, password);

        if (userResult == null) {
            responseBean.setResult(false);
            responseBean.setStatus(400);
            responseBean.setMsg("用户名或密码不正确");
            responseBean.setData(userBO.toString());
            return responseBean;
        }

        userResult = this.setNullProperty(userResult);

        CookieUtils.setCookie(request, response, "user", JsonUtils.objectToJson(userResult), true);

        responseBean.setResult(true);
        responseBean.setStatus(200);
        responseBean.setMsg("登录成功");
        responseBean.setData(userResult);
        return responseBean;
    }

    /**
     * 设置返回 null值保密
     * @param user 初始返回值
     * @return user
     */
    private Users setNullProperty(Users user) {
        user.setPassword(null);
        user.setMobile(null);
        user.setEmail(null);
        user.setCreatedTime(null);
        user.setUpdatedTime(null);
        user.setBirthday(null);
        return user;
    }

    @ApiOperation(value = "用户退出登录", notes = "用户退出登录", httpMethod = "POST")
    @PostMapping("/logout")
    public ResponseBean logout(@RequestParam String userId, HttpServletRequest request, HttpServletResponse response) {

        ResponseBean responseBean = new ResponseBean();

        // 清除用户的相关信息的cookie
        CookieUtils.deleteCookie(request, response, "user");

        // TODO: 2020/4/12  用户退出登录，需要清空购物车
        // TODO: 2020/4/12  分布式会话中需要清除用户数据

        responseBean.setStatus(200);
        responseBean.setResult(true);
        responseBean.setMsg("用户退出成功");
        responseBean.setData(null);
        return responseBean;
    }
}
