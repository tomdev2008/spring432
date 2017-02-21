package com.mvw.check;

import java.util.Date;

public class CheckResult {

	public Date time;/*当前时间*/
	
	public CheckStatus status;/*状态*/
	
	public String errorDesc;/*有错误时的描述*/
	
	public Object data;/*逻辑数据，建议传递json*/
}
