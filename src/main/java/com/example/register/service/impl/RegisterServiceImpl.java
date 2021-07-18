package com.example.register.service.impl;

import com.example.register.constant.Constants;
import com.example.register.domain.dao.BasRegisterDO;
import com.example.register.domain.dao.BasRegisterExample;
import com.example.register.domain.dao.BaseDO;
import com.example.register.domain.dao.mapper.BasRegisterMapper;
import com.example.register.domain.opt.PageOpt;
import com.example.register.domain.opt.RegisterOpt;
import com.example.register.domain.opt.RegisterSearchParams;
import com.example.register.domain.vo.RegisterVO;
import com.example.register.service.RegisterService;
import com.example.register.util.BeanCopyUtil;
import com.example.register.util.IdCardUtil;
import com.example.register.util.JsonUtil;
import com.example.register.util.UniqueUtils;
import com.github.pagehelper.PageHelper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 登记服务
 *
 * @author: Gary Gao(修远)
 * @date: 2021/7/16
 */
@Service
public class RegisterServiceImpl implements RegisterService {
    @Autowired(required = false)
    private BasRegisterMapper basRegisterMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean addRegister(RegisterOpt opt) {
        BasRegisterDO basRegisterDO = BeanCopyUtil.copyProperties(opt, BasRegisterDO.class);
        basRegisterDO.setRegisterId(UniqueUtils.getBusinessKey());
        BaseDO.initBaseDO(basRegisterDO);
        return basRegisterMapper.insert(basRegisterDO) > Constants.NUMBER_ZERO;
    }

    @Override
    public List<RegisterVO> listRegister(PageOpt opt) {
        BasRegisterExample example = new BasRegisterExample();
        BasRegisterExample.Criteria criteria = example.createCriteria().andDeleteStatusEqualTo(Boolean.FALSE);

        RegisterSearchParams searchParams = JsonUtil.parse(opt.getSearchParams(), RegisterSearchParams.class);
        if (searchParams != null) {
            if (StringUtils.isNotBlank(searchParams.getName())) {
                criteria.andNameLike("%" + searchParams.getName() + "%");
            }

            if (StringUtils.isNotBlank(searchParams.getPhone())) {
                criteria.andNameLike("%" + searchParams.getPhone() + "%");
            }

            if (StringUtils.isNotBlank(searchParams.getCommunity())) {
                criteria.andNameLike("%" + searchParams.getCommunity() + "%");
            }

            if (StringUtils.isNotBlank(searchParams.getIdCard())) {
                criteria.andNameLike("%" + searchParams.getIdCard() + "%");
            }

            SimpleDateFormat sdf = new SimpleDateFormat(" yyyy-MM-dd");
            sdf.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
            if (StringUtils.isNotBlank(searchParams.getStartExaminationTime())) {
                try {
                    criteria.andExaminationTimeGreaterThanOrEqualTo(sdf.parse(" " +
                            searchParams.getStartExaminationTime()));
                } catch (ParseException e) {
                    e.printStackTrace();
                    return Collections.emptyList();
                }
            }

            if (StringUtils.isNotBlank(searchParams.getEndExaminationTime())) {
                try {
                    criteria.andExaminationTimeLessThanOrEqualTo(sdf.parse(" " +
                            searchParams.getEndExaminationTime()));
                } catch (ParseException e) {
                    e.printStackTrace();
                    return Collections.emptyList();
                }
            }
        }

        Integer limit = opt.getLimit();
        Integer page = opt.getPage();
        if (limit != null && page != null) {
            PageHelper.startPage(page, limit);
        }

        List<BasRegisterDO> basRegisterDos = basRegisterMapper.selectByExample(example);
        return basRegisterDos.stream().map(basRegisterDO -> {
            String idCard = basRegisterDO.getIdCard();
            RegisterVO registerVO = BeanCopyUtil.copyProperties(basRegisterDO, RegisterVO.class);
            registerVO.setBirthTime(IdCardUtil.getBirthDate(idCard));
            registerVO.setSex(IdCardUtil.getSex(idCard));
            return registerVO;
        }).collect(Collectors.toList());
    }

    @Override
    public Long registerCount(PageOpt opt) {
        BasRegisterExample example = new BasRegisterExample();
        BasRegisterExample.Criteria criteria = example.createCriteria().andDeleteStatusEqualTo(Boolean.FALSE);

        RegisterSearchParams searchParams = JsonUtil.parse(opt.getSearchParams(), RegisterSearchParams.class);
        if (searchParams != null) {
            if (StringUtils.isNotBlank(searchParams.getName())) {
                criteria.andNameLike("%" + searchParams.getName() + "%");
            }

            if (StringUtils.isNotBlank(searchParams.getPhone())) {
                criteria.andNameLike("%" + searchParams.getPhone() + "%");
            }

            if (StringUtils.isNotBlank(searchParams.getCommunity())) {
                criteria.andNameLike("%" + searchParams.getCommunity() + "%");
            }

            if (StringUtils.isNotBlank(searchParams.getIdCard())) {
                criteria.andNameLike("%" + searchParams.getIdCard() + "%");
            }

            SimpleDateFormat sdf = new SimpleDateFormat(" yyyy-MM-dd");
            sdf.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
            if (StringUtils.isNotBlank(searchParams.getStartExaminationTime())) {
                try {
                    criteria.andExaminationTimeGreaterThanOrEqualTo(sdf.parse(" " +
                            searchParams.getStartExaminationTime()));
                } catch (ParseException e) {
                    e.printStackTrace();
                    return Constants.NUMBER_ZERO.longValue();
                }
            }

            if (StringUtils.isNotBlank(searchParams.getEndExaminationTime())) {
                try {
                    criteria.andExaminationTimeLessThanOrEqualTo(sdf.parse(" " +
                            searchParams.getEndExaminationTime()));
                } catch (ParseException e) {
                    e.printStackTrace();
                    return Constants.NUMBER_ZERO.longValue();
                }
            }
        }

        return basRegisterMapper.countByExample(example);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteRegister(Collection<String> ids) {
        BasRegisterExample example = new BasRegisterExample();
        example.createCriteria().andRegisterIdIn(new ArrayList<>(ids));

        BasRegisterDO basRegisterDO = new BasRegisterDO();
        BaseDO.deleteBaseDO(basRegisterDO);
        return basRegisterMapper.updateByExampleSelective(basRegisterDO, example) > Constants.NUMBER_ZERO;
    }

    @Override
    public boolean updateRegister(RegisterOpt opt) {
        BasRegisterDO basRegisterDO = BeanCopyUtil.copyProperties(opt, BasRegisterDO.class);
        return basRegisterMapper.updateByPrimaryKeySelective(basRegisterDO) > Constants.NUMBER_ZERO;
    }
}
