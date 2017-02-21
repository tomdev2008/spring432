package com.mvw.dao;

import java.util.List;
import java.util.Map;

import com.mvw.model.Student;
import com.mvw.mybatis.plugin.MapParam;

public interface StudentDao {

	//add
	int add(Student stu);
	int batchAdd(List<Student> list);
	
	//del
	int del(int id);
	int batchDel(List<Integer> ids);
	
	//update
	int update(Student stu);
	int updateNameAge(Student stu);
	
	//select
	Student selectById(int id);
	List<Student> select(Student stu);
	List<Student> selectUnion();
	
	
	//多参数传递
	
	//封装map
	public Map<Object, Object> getMap(MapParam param);  
}
