package com.example.register.util;

import java.util.UUID;

/**
 * 唯一值工具
 *
 * @author: Gary Gao(修远)
 * @date: 2021/7/17
 */
public class UniqueUtils {
   public static String getBusinessKey(){
       return UUID.randomUUID().toString().replaceAll("-","");
   }
}
