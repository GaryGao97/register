package com.example.register.domain.opt;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;

/**
 * @author: Gary Gao(修远)
 * @date: 2021/7/18
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RegisterSearchParams {
    private String name;
    private String idCard;
    private String phone;
    private String community;
    private String startExaminationTime;
    private String endExaminationTime;
    private List<String> ids;
}
