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
    private Date birthTime;
    private String address;
    private String phone;
    private String community;
    private Date examinationTime;
    private String remark;
}
