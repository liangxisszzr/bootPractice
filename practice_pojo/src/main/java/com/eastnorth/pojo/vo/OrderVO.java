package com.eastnorth.pojo.vo;

import com.eastnorth.pojo.bo.ShopcartBO;

import java.util.List;

/**
 * @author zuojianhou
 * @date   2020/4/17
 * @Description:
 */
public class OrderVO {

    private String orderId;
    private MerchantOrdersVO merchantOrdersVO;
    private List<ShopcartBO> toBeRemovedShopcatdList;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public MerchantOrdersVO getMerchantOrdersVO() {
        return merchantOrdersVO;
    }

    public void setMerchantOrdersVO(MerchantOrdersVO merchantOrdersVO) {
        this.merchantOrdersVO = merchantOrdersVO;
    }

    public List<ShopcartBO> getToBeRemovedShopcatdList() {
        return toBeRemovedShopcatdList;
    }

    public void setToBeRemovedShopcatdList(List<ShopcartBO> toBeRemovedShopcatdList) {
        this.toBeRemovedShopcatdList = toBeRemovedShopcatdList;
    }
}
