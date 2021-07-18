package com.example.register.domain.opt;

import lombok.Data;

/**
 * 分页请求
 * @author: Gary Gao(修远)
 * @date: 2021/7/18
 */
@Data
public class PageOpt {
    private Integer page;
    private Integer limit;
    private String searchParams;
}
