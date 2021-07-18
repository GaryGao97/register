package com.example.register.domain.opt;

import lombok.Data;

/**
 * @author: Gary Gao(修远)
 * @date: 2021/7/18
 */
@Data
public class RegisterSearchParams {
    private String name;
    private String idCard;
    private String phone;
    private String community;
    private String startExaminationTime;
    private String endExaminationTime;
}
