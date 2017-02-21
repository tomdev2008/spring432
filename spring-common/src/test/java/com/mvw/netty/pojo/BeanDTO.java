package com.mvw.netty.pojo;

import java.io.Serializable;

//继承Serializable 生成uid是必须
public class BeanDTO implements Serializable{
	
	private static final long serialVersionUID = 878675525785051409L;

	private Integer id=1;
	
	private String name="name123456";

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

	@Override
	public String toString() {
		return "BeanDTO [id=" + id + ", name=" + name + "]";
	}
}
