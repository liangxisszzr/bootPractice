package com.eastnorth.controller;

import com.eastnorth.pojo.Users;
import com.eastnorth.pojo.bo.ShopcartBO;
import com.eastnorth.pojo.bo.UserBO;
import com.eastnorth.pojo.vo.UsersVO;
import com.eastnorth.service.UserService;
import com.eastnorth.utils.CookieUtils;
import com.eastnorth.utils.JsonUtils;
import com.eastnorth.bean.ResponseBean;
import com.eastnorth.utils.RedisOperator;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author zuojianhou
 * @date   2020/4/8
 * @Description:
 */
@Api(value = "注册登录", tags = {"用于注册登录的相关接口"})
@RestController
@RequestMapping("/passport")
public class PassportController extends BaseController {

    @Autowired
    private UserService userService;
    @Autowired
    private RedisOperator redisOperator;

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

//        userResult = this.setNullProperty(userResult);

        // 实现用户的redis会话
        String uniqueToken = UUID.randomUUID().toString().trim();
        redisOperator.set(REDIS_USER_TOKEN + ":" + userResult.getId(), uniqueToken);

        UsersVO usersVO = new UsersVO();
        BeanUtils.copyProperties(userResult, usersVO);
        usersVO.setUserUniqueToken(uniqueToken);

        CookieUtils.setCookie(request, response, "user", JsonUtils.objectToJson(usersVO), true);

        // 同步购物车数据
        syncShopcartData(userResult.getId(), request, response);

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

        // TODO: 2020/4/15 生成用户token,存入redis会话 
        // 同步购物车数据
        syncShopcartData(userResult.getId(), request, response);

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
        CookieUtils.deleteCookie(request, response, FOODIE_SHOPCART);

        responseBean.setStatus(200);
        responseBean.setResult(true);
        responseBean.setMsg("用户退出成功");
        responseBean.setData(null);
        return responseBean;
    }

    /**
     * 注册登录成功后,同步cookie和redis中的购物车数据
     */
    private void syncShopcartData(String userId, HttpServletRequest request, HttpServletResponse response) {

        /**
         * 1. redis中无数据,如果cookie中的购物车为空,那么这个时候不作任何处理；如果cookie中购物车不为空,直接放入redis中
         * 2. redis中有数据,如果cookie中的购物车为空,那么直接把redis的购物车覆盖本地cookie
         * 3. redis中有数据,如果cookie中的购物车有数据,如果cookie中的某个商品在redis中存在,把cookie中的商品覆盖掉redis
         * 4. 同步到redis中去了以后,覆盖本地cookie购物车的数据,保证本地购物车的数据是同步最新的
         */

        // 从redis中获取购物车
        String shopcartJsonRedis = redisOperator.get(FOODIE_SHOPCART + ":" + userId);
        // 从cookie中获取购物车
        String shopcartStrCookie = CookieUtils.getCookieValue(request, FOODIE_SHOPCART, true);

        if (StringUtils.isBlank(shopcartJsonRedis)) {
            // redis为空,cookie不为空,直接把cookie中的数据放入redis
            if (StringUtils.isNotBlank((shopcartStrCookie))) {
                redisOperator.set(FOODIE_SHOPCART + ":" + userId, shopcartStrCookie);
            }
        } else {
            // redis不为空,cookie不为空,合并cookie和redis中购物车的商品数据(同一商品则覆盖)
            if (StringUtils.isNotBlank(shopcartStrCookie)) {
                /**
                 * 1. 已经存在的,把cookie中对应的数量,覆盖redis
                 * 2. 该项商品标记为待删除,统一放入一个带删除的list
                 * 3. 从cookie中清理所有的待删除list
                 * 4. 合并redis和cookie中的数据
                 * 5. 更新到redis和cookie中
                 */
                List<ShopcartBO> shopcartListRedis = JsonUtils.jsonToList(shopcartJsonRedis, ShopcartBO.class);
                List<ShopcartBO> shopcartListCookie = JsonUtils.jsonToList(shopcartStrCookie, ShopcartBO.class);

                // 定义一个待删除list
                List<ShopcartBO> pendingDeleteList = new ArrayList<>();

                for (ShopcartBO redisShopcart : shopcartListRedis) {
                    String redisSpecId = redisShopcart.getSpecId();

                    for (ShopcartBO cookieShopcart : shopcartListCookie) {
                        String cookieSpecId = cookieShopcart.getSpecId();

                        if (redisSpecId.equals(cookieSpecId)) {
                            // 覆盖购买数量，不累加，参考京东
                            redisShopcart.setBuyCounts(cookieShopcart.getBuyCounts());
                            // 把cookieShopcart放入待删除列表，用于最后的删除与合并
                            pendingDeleteList.add(cookieShopcart);
                        }

                    }
                }

                // 从现有cookie中删除对应的覆盖过的商品数据
                shopcartListCookie.removeAll(pendingDeleteList);

                // 合并两个list
                shopcartListRedis.addAll(shopcartListCookie);
                // 更新到redis和cookie
                CookieUtils.setCookie(request, response, FOODIE_SHOPCART, JsonUtils.objectToJson(shopcartListRedis), true);
                redisOperator.set(FOODIE_SHOPCART + ":" + userId, JsonUtils.objectToJson(shopcartListRedis));

            } else {
                // redis不为空,cookie为空,直接把redis覆盖cookie
                CookieUtils.setCookie(request, response, FOODIE_SHOPCART, shopcartJsonRedis, true);
            }
        }
    }
}
