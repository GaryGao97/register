package com.example.register.domain.dto;

import com.example.register.domain.vo.RegisterVO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 打印视图
 *
 * @author: Gary Gao(修远)
 * @date: 2021/7/19
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class RegisterPrintDTO extends RegisterVO {
    private Integer age;
    private String printDate;
    private String monthDay;
}
