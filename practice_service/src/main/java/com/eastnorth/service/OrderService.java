package com.eastnorth.service;

import com.eastnorth.pojo.bo.OrderSubmitBO;
import com.eastnorth.pojo.vo.OrderVO;

/**
 * @author zuojianhou
 */
public interface OrderService {

    /**
     * 用于创建订单相关信息
     * @param submitBO
     * @return
     */
    OrderVO createOrder(OrderSubmitBO submitBO);

    /**
     * 修改订单状态
     * @param orderId 订单 id
     * @param orderStatus 订单状态
     */
    void updateOrderStatus(String orderId, Integer orderStatus);
}
