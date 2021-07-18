package com.example.register.config.system;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * web 配置
 *
 * @author: Gary Gao(修远)
 * @date: 2021/7/17
 */
@Configuration
public class WebConfig {
    @Value("${thread-maximum-pool-size:2}")
    private Integer defaultPoolMaxSize;

    public Integer getDefaultPoolMaxSize() {
        return defaultPoolMaxSize;
    }
}
