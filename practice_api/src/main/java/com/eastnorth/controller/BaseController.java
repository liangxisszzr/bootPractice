package com.eastnorth.controller;

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
    static final Integer PAGE_SIZE = 20;

    /** cookie常量 */
    static final String FOODIE_SHOPCART = "shopcart";

    /** 微信支付成功 --> 支付中心 --> 平台项目 */
    static final String PAY_RETURN_URL = "http://localhost:8765/orders/notifyMerchanOrderPaid";

    /** 支付中心 url */
    static final String PAY_MENT_URL = "http://payment.t.mukewang.com/foodie-payment/payment/createMerchantOrder";

}
