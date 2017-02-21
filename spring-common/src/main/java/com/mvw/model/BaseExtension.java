package com.mvw.model;

import java.io.Serializable;

public class BaseExtension implements Serializable{

	private static final long serialVersionUID = 7974016032457677291L;

	//xxxStr的和数据库交互用:如下方法，建议抽取为共同工具，方便扩展:也可以序列化
	public String getFeature() {
		return null;
	}

	public void setFeatureStr(String feature) {
		
	}
}
