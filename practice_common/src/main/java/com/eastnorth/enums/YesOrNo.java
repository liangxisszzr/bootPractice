package com.eastnorth.enums;

/**
 * @author zuojianhou
 * @date   2020/4/13
 * @Description: 是否枚举
 */
public enum YesOrNo {

    /**
     * 否
     */
    NO(0, "否"),

    /**
     * 是
     */
    YES(1, "是");

    public final Integer type;
    public final String value;

    YesOrNo(Integer type, String value) {
        this.type = type;
        this.value = value;
    }
}
