package com.eastnorth.enums;

/**
 * 支付方式枚举
 */
public enum PayMethod {

    /**
     * 微信支付方式
     */
    WEIXIN(1, "微信"),

    /**
     * 支付宝支付方式
     */
    ALIPAY(2, "支付宝");

    public final Integer type;
    public final String value;

    PayMethod(Integer type, String value){
        this.type = type;
        this.value = value;
    }
}
