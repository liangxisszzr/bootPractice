package com.eastnorth.controller;

import com.eastnorth.enums.OrderStatusEnum;
import com.eastnorth.enums.PayMethod;
import com.eastnorth.pojo.OrderStatus;
import com.eastnorth.pojo.bo.OrderSubmitBO;
import com.eastnorth.pojo.bo.ShopcartBO;
import com.eastnorth.pojo.vo.MerchantOrdersVO;
import com.eastnorth.pojo.vo.OrderVO;
import com.eastnorth.service.OrderService;
import com.eastnorth.utils.CookieUtils;
import com.eastnorth.utils.IMOOCJSONResult;
import com.eastnorth.utils.JsonUtils;
import com.eastnorth.utils.RedisOperator;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author zuojianhou
 */
@Api(value = "订单相关", tags = {"订单相关的api接口"})
@RequestMapping("/orders")
@RestController
public class OrdersController extends BaseController {

    @Autowired
    private OrderService orderService;
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private RedisOperator redisOperator;

    @ApiOperation(value = "用户下单", notes = "用户下单", httpMethod = "POST")
    @PostMapping("/create")
    public IMOOCJSONResult create(@RequestBody OrderSubmitBO orderSubmitBO, HttpServletRequest request, HttpServletResponse response) {

        if (!orderSubmitBO.getPayMethod().equals(PayMethod.WEIXIN.type) && !orderSubmitBO.getPayMethod().equals(PayMethod.ALIPAY.type)) {
            return IMOOCJSONResult.errorMsg("支付方式不支持!");
        }

//        System.out.println(orderSubmitBO.toString());
        String shopCartJson = redisOperator.get(FOODIE_SHOPCART + ":" + orderSubmitBO.getUserId());
        if (StringUtils.isBlank(shopCartJson)) {
            return IMOOCJSONResult.errorMsg("购物车数据不正确");
        }

        List<ShopcartBO> shopCartList = JsonUtils.jsonToList(shopCartJson, ShopcartBO.class);

        // 1. 创建订单
        OrderVO orderVO =  orderService.createOrder(shopCartList, orderSubmitBO);
        String orderId = orderVO.getOrderId();

        // 2. 创建订单以后，移除购物车中已结算（已提交）的商品

        // 清理覆盖现有的redis汇总的购物数据
        shopCartList.removeAll(orderVO.getToBeRemovedShopcatdList());
        redisOperator.set(FOODIE_SHOPCART + ":" + orderSubmitBO.getUserId(), JsonUtils.objectToJson(shopCartList));
        // 整合redis之后，完善购物车中的已结算商品清除，并且同步到前端的cookie
        CookieUtils.setCookie(request, response, FOODIE_SHOPCART, JsonUtils.objectToJson(shopCartList), true);

        // 3. 向支付中心发送当前订单，用于保存支付中心的订单数据
        MerchantOrdersVO merchantOrdersVO = orderVO.getMerchantOrdersVO();
        merchantOrdersVO.setReturnUrl(PAY_RETURN_URL);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("imoocUserId", "imooc");
        headers.add("password", "imooc");

        HttpEntity<MerchantOrdersVO> entity = new HttpEntity<>(merchantOrdersVO, headers);

        ResponseEntity<IMOOCJSONResult> responseEntity = restTemplate.postForEntity(PAY_MENT_URL, entity, IMOOCJSONResult.class);
        IMOOCJSONResult paymentResult = responseEntity.getBody();
        if (paymentResult.getStatus() != 200) {
            return IMOOCJSONResult.errorMsg("支付中心订单创建失败,请联系管理员!");
        }

        return IMOOCJSONResult.ok(orderId);
    }

    @PostMapping("/notifyMerchantOrderPaid")
    public Integer notifyMerchantOrderPaid(String merchantOrderId) {

        orderService.updateOrderStatus(merchantOrderId, OrderStatusEnum.WAIT_DELIVER.type);

        return HttpStatus.OK.value();

    }

    @PostMapping("/getPaodOrderInfo")
    public IMOOCJSONResult getPaidOrderInfo(String orderId) {

        OrderStatus orderStatus = orderService.queryOrderStatusInfo(orderId);

        return IMOOCJSONResult.ok(orderStatus);

    }
}
