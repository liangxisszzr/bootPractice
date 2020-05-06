package com.eastnorth.controller;

import com.eastnorth.pojo.Orders;
import com.eastnorth.service.center.MyOrdersService;
import com.eastnorth.utils.IMOOCJSONResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 * @author zuojianhou
 * @date   2020/4/14
 * @Description:
 */
@Controller
public class BaseController {

    /** 分页常量 */
    static final Integer COMMENT_PAGE_SIZE = 10;
    protected static final Integer COMMON_PAGE_SIZE = 10;
    static final Integer PAGE_SIZE = 20;

    /** redis 单点登录 */
    public static final String REDIS_USER_TOKEN = "redis_user_token";

    /** cookie常量 */
    static final String FOODIE_SHOPCART = "shopcart";

    /** 微信支付成功 --> 支付中心 --> 平台项目 */
    static final String PAY_RETURN_URL = "http://192.168.204.128:8765/practice_api/orders/notifyMerchanOrderPaid";

    /** 支付中心 url */
    static final String PAY_MENT_URL = "http://payment.t.mukewang.com/foodie-payment/payment/createMerchantOrder";

    @Autowired
    public MyOrdersService myOrdersService;

    /**
     * 用于验证用户和订单是否有关联关系，避免非法用户调用
     * @return
     */
    public IMOOCJSONResult checkUserOrder(String userId, String orderId) {
        Orders order = myOrdersService.queryMyOrder(userId, orderId);
        if (order == null) {
            return IMOOCJSONResult.errorMsg("订单不存在！");
        }
        return IMOOCJSONResult.ok(order);
    }

}
