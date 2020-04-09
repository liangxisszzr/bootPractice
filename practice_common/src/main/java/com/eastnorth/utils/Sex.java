package com.eastnorth.utils;

/**
 * @author zuojianhou
 * @date   2020/4/9
 * @Description 性别枚举类
 */
public enum Sex {

    /**
     * 性别男
     */
    man(1, "男"),

    /**
     * 性别女
     */
    woman(0, "女"),

    /**
     * 性别保密
     */
    secret(2, "保密");

    public final Integer type;

    public final String value;

    Sex(Integer type, String value) {
        this.type = type;
        this.value = value;
    }
}
