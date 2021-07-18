package com.example.register.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.io.File;

/**
 * 静态路由
 *
 * @author: Gary Gao(修远)
 * @date: 2021/7/16
 */
@Controller
@RequestMapping("page")
public class IndexController {
    @RequestMapping("index")
    public String index(){
        return "index";
    }

    @RequestMapping("/**")
    public String index(HttpServletRequest req){
        String requestUri = req.getRequestURI();
        String contextPath = req.getContextPath();
        return requestUri.replace(contextPath + File.separator, "");
    }
}
