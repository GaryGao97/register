package com.example.register.domain.dao;

import com.fasterxml.jackson.annotation.JsonFormat;
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

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date examinationTime;

    private String remark;
}