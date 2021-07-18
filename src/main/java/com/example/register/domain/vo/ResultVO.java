package com.example.register.domain.vo;

import com.example.register.config.exception.BaseErrorInfoInterface;
import com.example.register.constant.Constants;
import com.example.register.enums.ErrorEnum;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 结果视图
 * @author: Gary Gao(修远)
 * @date: 2021/7/17
 */
@Data
@NoArgsConstructor
public class ResultVO {
    private String code;
    private String msg;
    private Long count;
    private Object data;

    public ResultVO(BaseErrorInfoInterface errorInfo) {
        this.code = errorInfo.getErrorCode();
        this.msg = errorInfo.getErrorMsg();
    }

    /**
     * 成功
     *
     * @return
     */
    public static ResultVO success() {
        return success(null);
    }

    /**
     * 成功
     * @param data
     * @return
     */
    public static ResultVO success(Object data) {
        ResultVO resultVO = new ResultVO();
        resultVO.setCode(Constants.SUCCESS_CODE);
        resultVO.setMsg(Constants.SUCCESS_MSG);
        resultVO.setData(data);
        return resultVO;
    }

    /**
     * 失败
     */
    public static ResultVO error(BaseErrorInfoInterface errorInfo) {
        ResultVO resultVO = new ResultVO();
        resultVO.setCode(errorInfo.getErrorCode());
        resultVO.setMsg(errorInfo.getErrorMsg());
        return resultVO;
    }

    /**
     * 失败
     */
    public static ResultVO error(String code, String message) {
        ResultVO resultVO = new ResultVO();
        resultVO.setCode(code);
        resultVO.setMsg(message);
        return resultVO;
    }

    /**
     * 失败
     */
    public static ResultVO error(String message) {
        ResultVO resultVO = new ResultVO();
        resultVO.setCode(String.valueOf(Constants.NUMBER_MINUS_ONE));
        resultVO.setMsg(message);
        return resultVO;
    }
}
