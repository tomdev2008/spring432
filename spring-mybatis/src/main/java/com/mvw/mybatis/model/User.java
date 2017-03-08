package com.mvw.mybatis.model;

import java.util.List;

public class User {

	private Integer id;
	
	private Integer pid;

	private String name;

	private MyBean myBean;//javaBean

	private List<SubBean> list;//集合

	
    public Integer getId() {
    	return id;
    }

	
    public void setId(Integer id) {
    	this.id = id;
    }

	
    public String getName() {
    	return name;
    }

	
    public void setName(String name) {
    	this.name = name;
    }

	
    public MyBean getMyBean() {
    	return myBean;
    }

	
    public void setMyBean(MyBean myBean) {
    	this.myBean = myBean;
    }

	
    public List<SubBean> getList() {
    	return list;
    }

	
    public void setList(List<SubBean> list) {
    	this.list = list;
    }

	public Integer getPid() {
		return pid;
	}


	public void setPid(Integer pid) {
		this.pid = pid;
	}


	@Override
    public String toString() {
	    return "User [id=" + id + ", name=" + name + ", myBean=" + myBean + ", list=" + list + "]";
    }
}
