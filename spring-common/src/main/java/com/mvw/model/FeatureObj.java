package com.mvw.model;

import java.io.Serializable;

/**
 * 扩展属性，以大json串的形式形式存储到数据库
 * 
 * 
 * @author gaotingping
 *
 * 2016年11月17日 上午11:13:18
 */
public class FeatureObj implements Serializable{

	private static final long serialVersionUID = -9079793332269384459L;

	private String ctime;
	
	private String remark;
	
	private String finshTime;
	
	private String tt;

	public String getCtime() {
		return ctime;
	}

	public void setCtime(String ctime) {
		this.ctime = ctime;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getFinshTime() {
		return finshTime;
	}

	public void setFinshTime(String finshTime) {
		this.finshTime = finshTime;
	}

	public String getTt() {
		return tt;
	}

	public void setTt(String tt) {
		this.tt = tt;
	}

	@Override
	public String toString() {
		return "FeatureObj [ctime=" + ctime + ", remark=" + remark + ", finshTime=" + finshTime + "]";
	}
}
