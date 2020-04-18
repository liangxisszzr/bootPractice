package com.eastnorth.config;

import com.eastnorth.service.OrderService;
import com.eastnorth.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author zuojianhou
 * @date   2020/4/18
 * @Description:
 */
@Component
public class OrderJob {

    @Autowired
    private OrderService orderService;

    /**
     * 使用定时任务关闭超期未支付订单，会存在的弊端
     * 1. 会有时间差：10:39下单，11:00检查不足1小时，12:00检查，超过1小时多余39分钟
     * 2. 不支持集群：单机没问题，使用集群后会有多个定时任务 解决方案：只使用一台计算机节点，单独用来运行所有的定时任务
     * 3. 会对数据库全表搜索，极其影响数据库性能
     * 4. 定时任务，仅仅只适用于小型轻量级项目，传统项目
     *
     * 后续课程会涉及到消息队列：MQ->RabbitMQ，RocketMQ，Kafka，ZeroMQ...
     *      延时任务（队列）
     *      10:12分下单的，未付款（10）状态，11:12分检查，如果当前状态还是10，则直接关闭订单
     */
    @Scheduled(cron = "0/3 * * * * ?")
    public void autoCloseOrder() {
        orderService.closeOrder();
        System.out.println("执行定时任务,当前时间为:" + DateUtil.getCurrentDateString(DateUtil.DATETIME_PATTERN));
    }

}
