package com.mvw.cpm.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;

@Entity(name = "m2")
public class MBean2 extends BaseEntity{

	private static final long serialVersionUID = -3315184292982842282L;

	private String name;

	@ManyToMany
	private List<MBean1> m1;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
    public List<MBean1> getM1() {
    	return m1;
    }
	
    public void setM1(List<MBean1> m1) {
    	this.m1 = m1;
    }
}
