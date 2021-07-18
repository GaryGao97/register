package com.example.register.enums;

import com.example.register.config.exception.BaseErrorInfoInterface;

/**
 * 错误
 *
 * @author: Gary Gao(修远)
 * @date: 2021/7/16
 */
public enum ErrorEnum implements BaseErrorInfoInterface {
    /*
     * 错误信息
     * */
    E_400("400", "请求处理异常，请稍后再试"),
    E_500("500", "请求方式有误,请检查 GET/POST"),
    E_501("501", "请求路径不存在"),
    E_502("502", "权限不足"),
    E_10008("10008", "角色删除失败,尚有用户属于此角色"),
    E_10009("10009", "账户已存在"),
    E_10010("10010", "账号/密码错误"),

    E_20011("20011", "登陆已过期,请重新登陆"),

    E_90003("90003", "缺少必填参数"),
    E_90004("90004", "操作失败"),

    BODY_NOT_MATCH("400","请求的数据格式不符!"),
    INTERNAL_SERVER_ERROR("500", "服务器内部错误!");

    private final String errorCode;

    private final String errorMsg;

    ErrorEnum(String errorCode, String errorMsg) {
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

    @Override
    public String getErrorCode() {
        return errorCode;
    }

    @Override
    public String getErrorMsg() {
        return errorMsg;
    }
}
