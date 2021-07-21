package com.example.register.domain.dao.mapper;

import com.example.register.domain.dao.BasRegisterDO;
import com.example.register.domain.dao.BasRegisterExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BasRegisterMapper {
    long countByExample(BasRegisterExample example);

    int deleteByExample(BasRegisterExample example);

    int deleteByPrimaryKey(String registerId);

    int insert(BasRegisterDO record);

    int insertBatch(List<BasRegisterDO> list);

    int insertSelective(BasRegisterDO record);

    List<BasRegisterDO> selectByExample(BasRegisterExample example);

    BasRegisterDO selectByPrimaryKey(String registerId);

    int updateByExampleSelective(@Param("record") BasRegisterDO record, @Param("example") BasRegisterExample example);

    int updateByExample(@Param("record") BasRegisterDO record, @Param("example") BasRegisterExample example);

    int updateByPrimaryKeySelective(BasRegisterDO record);

    int updateByPrimaryKey(BasRegisterDO record);
}