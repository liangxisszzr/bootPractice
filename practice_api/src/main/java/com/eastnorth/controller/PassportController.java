package com.eastnorth.controller;

import com.eastnorth.pojo.bo.UserBO;
import com.eastnorth.service.UserService;
import com.eastnorth.utils.ResponseBean;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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
    @PostMapping("/register")
    public ResponseBean register(@RequestBody UserBO userBO) {

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
        userService.createUser(userBO);
        responseBean.setResult(true);
        responseBean.setStatus(200);
        responseBean.setMsg("用户创建成功");
        responseBean.setData(userBO.toString());
        return responseBean;
    }
}
