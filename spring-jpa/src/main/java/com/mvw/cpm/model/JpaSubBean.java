package com.mvw.cpm.model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity(name = "jpa2")
public class JpaSubBean extends BaseEntity {

	private static final long serialVersionUID = -3315184292982842282L;

	private Integer pid;

	private String name;
	
	@ManyToOne
	private JpaBean  jpaBean;

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
}
