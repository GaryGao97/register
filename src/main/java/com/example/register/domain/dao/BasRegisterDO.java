package com.example.register.domain.dao;

import lombok.Data;

import java.util.Date;

@Data
public class BasRegisterDO extends BaseDO {
    private String registerId;

    private String name;

    private String idCard;

    private String address;

    private String phone;

    private String community;

    private Date examinationTime;

    private String remark;
}