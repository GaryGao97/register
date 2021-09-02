package com.example.register.service;


import com.example.register.domain.dao.BasRegisterDO;
import com.example.register.domain.opt.PageOpt;
import com.example.register.domain.opt.RegisterOpt;
import com.example.register.domain.opt.RegisterSearchParams;
import com.example.register.domain.vo.RegisterVO;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.Collection;
import java.util.List;

/**
 * 登记服务
 *
 * @author: Gary Gao(修远)
 * @date: 2021/7/17
 */
public interface RegisterService {
    /**
     * 添加登记
     *
     * @param opt
     * @return
     */
    boolean addRegister(RegisterOpt opt);

    /**
     * 登记列表
     *
     * @param opt
     * @return
     */
    List<RegisterVO> listRegister(PageOpt opt);

    /**
     * 登记数量
     *
     * @param opt
     * @return
     */
    Long registerCount(PageOpt opt);

    /**
     * 删除登记
     *
     * @param ids
     * @return
     */
    boolean deleteRegister(Collection<String> ids);

    /**
     * 修改登记
     *
     * @param opt
     * @return
     */
    boolean updateRegister(RegisterOpt opt);

    /**
     * 导出
     *
     * @param opt
     * @param response
     * @return
     */
    void exportRegister(RegisterOpt opt, HttpServletResponse response);

    /**
     * 导出
     *
     * @param opt
     * @param response
     * @return
     */
    void exportRegisterAll(RegisterSearchParams opt, HttpServletResponse response);

    /**
     * 获取登记
     *
     * @param idCard
     * @return
     */
    BasRegisterDO getRegister(String idCard);

    /**
     * 文件导入
     * @param file
     * @return
     */
    boolean importRegister(MultipartFile file);
}
