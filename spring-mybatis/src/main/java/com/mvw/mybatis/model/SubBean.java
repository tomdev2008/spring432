package com.mvw.mybatis.model;

public class SubBean{

	private Integer id;

	private Integer pid;

	private String name;

	
    public Integer getId() {
    	return id;
    }

	
    public void setId(Integer id) {
    	this.id = id;
    }

	
    public Integer getPid() {
    	return pid;
    }

	
    public void setPid(Integer pid) {
    	this.pid = pid;
    }

	
    public String getName() {
    	return name;
    }

	
    public void setName(String name) {
    	this.name = name;
    }


	@Override
    public String toString() {
	    return "SubBean [id=" + id + ", pid=" + pid + ", name=" + name + "]";
    }
}
