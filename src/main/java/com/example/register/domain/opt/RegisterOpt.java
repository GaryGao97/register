package com.example.register.domain.opt;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.Date;

/**
 * @author: Gary Gao(修远)
 * @date: 2021/7/18
 */
@Data
public class RegisterOpt {
    private String registerId;

    private String name;

    private String idCard;

    private String address;

    private String phone;

    private String community;

    private Date examinationTime;

    private String remark;
}
