package com.mvw.model;

import java.util.List;

import com.alibaba.fastjson.JSON;

public class Student {

	//普通属性
	private int id;

	private String name;
	
	private int age;
	
	//集合
	private List<Course> courses;
	
	//扩展
	private FeatureObj feature;
	
	//xxxStr的和数据库交互用:如下方法，建议抽取为共同工具，方便扩展:也可以序列化
	public String getFeatureStr() {
		return JSON.toJSONString(feature);
	}

	public void setFeatureStr(String feature) {
		this.feature = JSON.parseObject(feature, FeatureObj.class); ;
	}

	//正常获取
	public FeatureObj getFeature() {
		return feature;
	}

	public void setFeature(FeatureObj feature) {
		this.feature = feature;
	}

	public Student() {
		super();
	}

	public Student(String name) {
		super();
		this.name = name;
	}
	

	public Student(String name, int age) {
		super();
		this.name = name;
		this.age = age;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Course> getCourses() {
		return courses;
	}

	public void setCourses(List<Course> courses) {
		this.courses = courses;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	@Override
	public String toString() {
		return "Student [id=" + id + ", name=" + name + ", age=" + age + ", courses=" + courses + ", feature=" + feature
				+ "]";
	}
}
