package com.eastnorth.mapper;

import com.eastnorth.pojo.OrderStatus;
import com.eastnorth.pojo.vo.MyOrdersVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface OrdersMapperCustom {

    /**
     * 查询订单
     * @param map 查询条件
     * @return 订单列表集合
     */
    List<MyOrdersVO> queryMyOrders(@Param("paramsMap") Map<String, Object> map);

    /**
     * 获取不同状态订单总数
     * @param map 查询条件
     * @return 总数
     */
    int getMyOrderStatusCounts(@Param("paramsMap") Map<String, Object> map);

    /**
     * 订单动向查询
     * @param map 查询条件
     * @return 订单状态结果集
     */
    List<OrderStatus> getMyOrderTrend(@Param("paramsMap") Map<String, Object> map);

}
