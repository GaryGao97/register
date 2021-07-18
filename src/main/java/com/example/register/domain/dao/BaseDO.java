package com.example.register.domain.dao;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 基础
 *
 * @author: Gary Gao(修远)
 * @date: 2021/7/17
 */
@Data
public class BaseDO implements Serializable {
   /** 删除状态 */
   private Boolean deleteStatus;

   /** 更新时间 */
   private Date updateTime;

   /** 创建时间 */
   private Date createTime;

   public static <T extends BaseDO> T initBaseDO(T t) {
       if (t == null) {
           return null;
       }
       Date currentDate = new Date();
       t.setCreateTime(currentDate);
       t.setUpdateTime(currentDate);
       t.setDeleteStatus(Boolean.FALSE);
       return t;
   }


   public static <T extends BaseDO> T deleteBaseDO(T t) {
       if (t == null) {
           return null;
       }
       t.setUpdateTime(new Date());
       t.setDeleteStatus(Boolean.TRUE);
       return t;
   }
}
