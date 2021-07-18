package com.example.register.controller;

import com.example.register.util.JsonUtil;
import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author: Gary Gao(修远)
 * @date: 2021/7/17
 */
@RestController
@RequestMapping("menu")
public class MenuController {
    /**
     * 登录
     */
    @GetMapping("/list")
    public Map<String, Object> menu() {
        String menu = "{\n" +
                "    \"homeInfo\": {\n" +
                "        \"title\": \"首页\",\n" +
                "        \"href\": \"table\"\n" +
                "    },\n" +
                "    \"logoInfo\": {\n" +
                "        \"title\": \"Idun\",\n" +
                "        \"image\": \"../static/images/logo.png\",\n" +
                "        \"href\": \"\"\n" +
                "    },\n" +
                "    \"menuInfo\": [\n" +
                "        {\n" +
                "            \"title\": \"常规管理\",\n" +
                "            \"icon\": \"fa fa-address-book\",\n" +
                "            \"href\": \"\",\n" +
                "            \"target\": \"_self\",\n" +
                "            \"child\": [\n" +
                "                {\n" +
                "                    \"title\": \"体检登记\",\n" +
                "                    \"href\": \"table\",\n" +
                "                    \"icon\": \"fa fa-file-text\",\n" +
                "                    \"target\": \"_self\"\n" +
                "                }\n" +
                "            ]\n" +
                "        }\n" +
                "    ]\n" +
                "}";

        return JsonUtil.parse(menu, new TypeReference<Map<String, Object>>() {
        });
    }
}
