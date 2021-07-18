package com.example.register.config.exception;

/**
 * 错误信息接口
 *
 * @author: Gary Gao(修远)
 * @date: 2021/7/18
 */
public interface BaseErrorInfoInterface {
	/**
	 * 错误码
	 *
	 * @return
	 */
    String getErrorCode();

	/**
	 * 错误信息
	 *
	 * @return
	 */
	String getErrorMsg();
}