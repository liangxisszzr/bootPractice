package com.eastnorth.service;

import com.eastnorth.pojo.bo.OrderSubmitBO;

public interface OrderService {

    /**
     * 用于创建订单相关信息
     * @param submitBO
     */
    void createOrder(OrderSubmitBO submitBO);
}
