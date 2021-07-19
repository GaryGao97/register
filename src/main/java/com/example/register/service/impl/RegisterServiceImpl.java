package com.example.register.service.impl;

import com.example.register.constant.Constants;
import com.example.register.domain.dao.BasRegisterDO;
import com.example.register.domain.dao.BasRegisterExample;
import com.example.register.domain.dao.BaseDO;
import com.example.register.domain.dao.mapper.BasRegisterMapper;
import com.example.register.domain.dto.RegisterPrintDTO;
import com.example.register.domain.opt.PageOpt;
import com.example.register.domain.opt.RegisterOpt;
import com.example.register.domain.opt.RegisterSearchParams;
import com.example.register.domain.vo.RegisterVO;
import com.example.register.service.RegisterService;
import com.example.register.util.*;
import com.github.pagehelper.PageHelper;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
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
                criteria.andPhoneLike("%" + searchParams.getPhone() + "%");
            }

            if (StringUtils.isNotBlank(searchParams.getCommunity())) {
                criteria.andCommunityEqualTo(searchParams.getCommunity());
            }

            if (StringUtils.isNotBlank(searchParams.getIdCard())) {
                criteria.andIdCardLike("%" + searchParams.getIdCard() + "%");
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
            registerVO.setBirthTime(IdCardUtil.getBirthDateStr(idCard));
            registerVO.setSex(IdCardUtil.getSexByCard(idCard));

            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            registerVO.setExaminationTime(format.format(basRegisterDO.getExaminationTime()));
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
                criteria.andPhoneLike("%" + searchParams.getPhone() + "%");
            }

            if (StringUtils.isNotBlank(searchParams.getCommunity())) {
                criteria.andCommunityEqualTo(searchParams.getCommunity());
            }

            if (StringUtils.isNotBlank(searchParams.getIdCard())) {
                criteria.andIdCardLike("%" + searchParams.getIdCard() + "%");
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

    @Override
    public void exportRegister(RegisterOpt opt, HttpServletResponse res) {
        String idCard = opt.getIdCard();
        BasRegisterDO basRegisterDO = getRegister(idCard);
        if (basRegisterDO == null) {
            return;
        }

        RegisterPrintDTO printDTO = BeanCopyUtil.copyProperties(basRegisterDO, RegisterPrintDTO.class);
        printDTO.setBirthTime(IdCardUtil.getBirthDateStr(idCard));
        printDTO.setSex(IdCardUtil.getSexByCard(idCard));
        printDTO.setAge(IdCardUtil.getAgeByCard(idCard));
        printDTO.setPrintDate(DateTime.now().toString("报告日期：   yyyy 年 MM 月 dd 日      检验者"));
        printDTO.setMonthDay(DateTime.now().toString("MM月dd日"));

        String fileName = "血常规化验单-" + DateTime.now().toString("yyyyMMddHHmmss");
        Map<String, Object> model = Collections.singletonMap("item", printDTO);

        try {
            JxlsTemplate.processTemplate(res, "routineBloodTestSheet.xlsx", fileName, model);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void exportRegisterAll(RegisterOpt opt, HttpServletResponse res) {
        PageOpt pageOpt = new PageOpt();
        pageOpt.setSearchParams(JsonUtil.toJsonString(opt));
        List<RegisterVO> registerVos = listRegister(pageOpt);
        if (CollectionUtils.isEmpty(registerVos)) {
            return;
        }

        String fileName = "体检登记-" + DateTime.now().toString("yyyyMMddHHmmss");
        Map<String, Object> model = Collections.singletonMap("entries", registerVos);

        try {
            JxlsTemplate.processTemplate(res, "medicalRegistration.xlsx", fileName, model);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public BasRegisterDO getRegister(String idCard) {
        BasRegisterExample example = new BasRegisterExample();
        example.createCriteria().andDeleteStatusEqualTo(Boolean.FALSE).andIdCardEqualTo(idCard);

        List<BasRegisterDO> basRegisterDos = basRegisterMapper.selectByExample(example);
        if (CollectionUtils.isEmpty(basRegisterDos)) {
            return null;
        }


        return basRegisterDos.get(Constants.NUMBER_ZERO);
    }
}
