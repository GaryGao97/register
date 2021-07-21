package com.example.register.controller;

import com.example.register.domain.dao.BasRegisterDO;
import com.example.register.domain.opt.PageOpt;
import com.example.register.domain.opt.RegisterImportOpt;
import com.example.register.domain.opt.RegisterOpt;
import com.example.register.domain.vo.RegisterVO;
import com.example.register.domain.vo.ResultVO;
import com.example.register.enums.ErrorEnum;
import com.example.register.service.RegisterService;
import com.example.register.util.BeanCopyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 登记
 *
 * @author: Gary Gao(修远)
 * @date: 2021/7/16
 */
@RestController
@RequestMapping("register")
public class RegisterController {
    @Autowired
    private RegisterService registerService;

    /**
     * 查询
     *
     * @param opt
     * @return
     */
    @GetMapping("list")
    public ResultVO listRegister(PageOpt opt) {
        return ResultVO.success(registerService.listRegister(opt), registerService.registerCount(opt));
    }

    /**
     * 添加
     *
     * @param opt
     * @return
     */
    @PostMapping("add")
    public ResultVO addRegister(@RequestBody RegisterOpt opt) {
        return registerService.addRegister(opt) ? ResultVO.success() : ResultVO.error(ErrorEnum.E_90004);
    }

    /**
     * 添加
     *
     * @param opt
     * @return
     */
    @PostMapping("add-plus")
    public ResultVO addPlus(@RequestBody RegisterOpt opt) {
        BasRegisterDO basRegisterDO = registerService.getRegister(opt.getIdCard());
        if (basRegisterDO != null) {
            Date examinationTime = basRegisterDO.getExaminationTime();
            if (examinationTime.getYear() == new Date().getYear()) {
                RegisterVO registerVO = BeanCopyUtil.copyProperties(basRegisterDO, RegisterVO.class);
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                registerVO.setExaminationTime(format.format(basRegisterDO.getExaminationTime()));
                return ResultVO.success(ErrorEnum.REGISTERED.getErrorCode(),
                        ErrorEnum.REGISTERED.getErrorMsg(), registerVO);
            }

            return ResultVO.success(ErrorEnum.DATA_ALREADY_EXISTS.getErrorCode(),
                    ErrorEnum.DATA_ALREADY_EXISTS.getErrorMsg(), null);

        }

        return registerService.addRegister(opt) ? ResultVO.success() : ResultVO.error(ErrorEnum.E_90004);
    }

    /**
     * 导出
     *
     * @param opt
     * @return
     */
    @GetMapping("export")
    public void exportRegister(RegisterOpt opt, HttpServletResponse response) {
        registerService.exportRegister(opt, response);
    }

    /**
     * 导出所有
     *
     * @param opt
     * @return
     */
    @GetMapping("export-all")
    public void exportRegisterAll(RegisterOpt opt, HttpServletResponse response) {
        registerService.exportRegisterAll(opt, response);
    }

    /**
     * 更新
     *
     * @param opt
     * @return
     */
    @PostMapping("update")
    public ResultVO updateRegister(@RequestBody RegisterOpt opt) {
        return registerService.updateRegister(opt) ? ResultVO.success() : ResultVO.error(ErrorEnum.E_90004);
    }

    /**
     * 删除
     *
     * @param opt
     * @return
     */
    @PostMapping("delete")
    public ResultVO deleteRegister(@RequestBody List<RegisterOpt> opt) {
        Set<String> ids = opt.stream().map(RegisterOpt::getRegisterId).collect(Collectors.toSet());
        return registerService.deleteRegister(ids) ? ResultVO.success() : ResultVO.error(ErrorEnum.E_90004);
    }

    /**
     * 导入
     *
     * @param opt
     * @return
     */
    @PostMapping("import")
    public ResultVO importRegister(RegisterImportOpt opt) {
        if (opt.getFile() == null) {
            return ResultVO.error("文件不存在");
        }

        return registerService.importRegister(opt.getFile()) ? ResultVO.success() :
                ResultVO.error(ErrorEnum.FILE_NOT_EXISTS);
    }
}
