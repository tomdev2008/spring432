package com.mvw.mybatils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.mvw.dao.StudentDao;
import com.mvw.model.FeatureObj;
import com.mvw.model.Student;
import com.mvw.mybatis.plugin.MapParam;

/**
 * 多参数传递总结
 * 
 * mapper自动生成，参数统一搞成Map(避免封装多个数据bean)
 * 在dao中再把参数剥离出来，方便别人使用
 * 
 * @author gaotingping
 *
 * 2016年8月19日 上午11:02:32
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring-servlet.xml" })
public class CrudTest {

	@Autowired
	private StudentDao dao;

	@Test
	public void test() {

		Student stu = new Student("测试1");
		dao.add(stu);

		List<Student> stus = new ArrayList<Student>();
		for (int i = 0; i < 10; i++) {
			Student s = new Student("测试batch_" + i);
			stus.add(s);
		}
		dao.batchAdd(stus);

	}

	@Test
	public void testDel() {

		dao.del(17);

		List<Integer> ids = new ArrayList<Integer>();
		ids.add(16);
		ids.add(15);
		dao.batchDel(ids);
	}

	@Test
	public void testUpdate() {
		Student stu = new Student("测试111");
		stu.setId(7);
		dao.update(stu);
	}

	@Test
	public void testUpdate2() {
		Student stu = new Student("测试222", 15);
		stu.setId(9);
		dao.updateNameAge(stu);
	}

	@Test
	public void testAdd(){
		Student s = new Student("测试大json");
		
		FeatureObj f=new FeatureObj();
		f.setCtime("首回合");
		f.setFinshTime("斤斤计较");
		f.setRemark("去是");
		
		s.setFeature(f);
		
		dao.add(s);
	}
	
	@Test
	public void testSel() {
		Student stu = dao.selectById(96);
		System.out.println(stu);
	}

	@Test
	public void testSel2() {
		Student s = new Student();
		s.setAge(15);

		List<Student> list = dao.select(s);
		System.out.println(list);
	}

	@Test
	public void testSel3() {
		List<Student> list = dao.selectUnion();
		System.out.println(list);
	}

	@Test
	public void test15() {
		MapParam param = new MapParam("id",null);
		Map<Object, Object> result = dao.getMap(param);
		System.out.println(result);
	}
}
