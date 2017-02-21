package com.mvw.cpm.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;

@Entity(name="m1")
public class MBean1 extends BaseEntity{

    private static final long serialVersionUID = -3315184292982842282L;

	private String name;
	
	//m1放弃关系维护，让m2维护
	@ManyToMany(mappedBy="m1")
	private List<MBean2> m2;

    public String getName() {
    	return name;
    }

	
    public void setName(String name) {
    	this.name = name;
    }


	
    public List<MBean2> getM2() {
    	return m2;
    }


	
    public void setM2(List<MBean2> m2) {
    	this.m2 = m2;
    }
}
