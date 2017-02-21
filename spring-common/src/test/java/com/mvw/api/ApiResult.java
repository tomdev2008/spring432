package com.mvw.api;

import java.util.Date;

/**
 * 公共api返回定义
 * 
 * @author gaotingping
 *
 * 2016年8月4日 下午2:23:26
 */
public class ApiResult {

	private boolean status;/*状态:指的是接口是否调用成功，非业务逻辑处理状态*/
	
	private String uuid;/*分布式系统多个系统之间交互,每个请求给uuid，方便后期查询*/
	
	private long stime=new Date().getTime();/*服务器当前时间*/
	
	private Object data;/*具体的业务数据*/
	
	private ApiErrorCode errorCode;/*status=false时错误状态说明*/

	public void setStatus(boolean status) {
		this.status = status;
	}
	
	public boolean getStatus() {
		return status;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public long getStime() {
		return stime;
	}

	public void setStime(long stime) {
		this.stime = stime;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public ApiErrorCode getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(ApiErrorCode errorCode) {
		this.errorCode = errorCode;
	}
}
