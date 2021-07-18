package com.example.register.enums;

/**
 * 性别枚举
 *
 * @author: Gary Gao(修远)
 * @date: 2021/7/17
 */
public enum SexEnum {
    /**
     * 性别
     */
    MAN("男"),
    WOMAN("女");

    private final String label;

    SexEnum(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
