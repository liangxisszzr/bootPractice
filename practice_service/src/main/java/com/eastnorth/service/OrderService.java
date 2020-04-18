package com.eastnorth.service;

import com.eastnorth.pojo.OrderStatus;
import com.eastnorth.pojo.bo.OrderSubmitBO;
import com.eastnorth.pojo.vo.OrderVO;

/**
 * @author zuojianhou
 */
public interface OrderService {

    /**
     * 用于创建订单相关信息
     * @param submitBO 提交信息
     * @return 订单信息
     */
    OrderVO createOrder(OrderSubmitBO submitBO);

    /**
     * 修改订单状态
     * @param orderId 订单 id
     * @param orderStatus 订单状态
     */
    void updateOrderStatus(String orderId, Integer orderStatus);

    /**
     * 查询订单状态
     * @param orderId 订单id
     * @return 订单状态信息
     */
    OrderStatus queryOrderStatusInfo(String orderId);

    /**
     * 关闭超时未支付订单
     */
    void closeOrder();
}
