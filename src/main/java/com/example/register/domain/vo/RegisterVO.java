package com.example.register.domain.vo;

import lombok.Data;

import java.util.Date;

/**
 * 登记视图
 *
 * @author: Gary Gao(修远)
 * @date: 2021/7/17
 */
@Data
public class RegisterVO {
    private String registerId;
    private String name;
    private String idCard;
    private String sex;
    private Integer age;
    private String birthTime;
    private String address;
    private String phone;
    private String community;
    private String examinationTime;
    private String remark;
}
