package com.mvw.mybatils;

import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.mvw.dao.StudentDao;
import com.mvw.model.Student;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/spring-db-rw.xml" })
public class TestRW {

	@Autowired
	private StudentDao dao;

	/**
	 * 重写SqlSession的实现类,按照当前整合方式
	 * 重写DefaultSqlSession即可
	 * 
	 * 这里仅仅测试，实际中直接覆盖jar即可
	 */
	@Test
	public void test1() {
		//select 1
		Student s = dao.selectById(34);//查询
		System.out.println(s);
		
		//select 2
		Map<Object, Object> map = dao.getMap(null);
		System.out.println(map);
		
		//update 1
		Student stu=new Student();
		stu.setAge(67);
		stu.setId(67);
		stu.setName("name_67");
		int len = dao.update(stu); //修改测试
		System.out.println(len);
		
		//select 3
		map = dao.getMap(null);
		System.out.println(map);
	}
}
