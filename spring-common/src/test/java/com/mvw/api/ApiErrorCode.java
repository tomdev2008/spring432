package com.mvw.api;

/**
 * api公共错误码
 * 
 * @author gaotingping
 *
 * 2016年8月4日 下午2:23:26
 */
public enum ApiErrorCode {
	
	SYSTEM_ERROR(1,"系统错误"),
	PARAM_ERROR(3,"参数错误");
	
	int code;/*错误码*/
	String state;/*错误说明*/
	
	private ApiErrorCode(int code, String state) {
		this.code = code;
		this.state = state;
	}

	public int getCode() {
		return code;
	}
	
	public void setCode(int code) {
		this.code = code;
	}
	
	public String getState() {
		return state;
	}
	
	public void setState(String state) {
		this.state = state;
	}
}
