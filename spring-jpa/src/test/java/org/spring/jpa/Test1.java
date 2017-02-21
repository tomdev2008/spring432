package org.spring.jpa;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.mvw.cpm.dao.JpaDao;
import com.mvw.cpm.model.JpaBean;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring-servlet.xml" })
public class Test1 {

	@Autowired
	private JpaDao jpaDao;
	
	@Test
	public void test(){
		
		JpaBean jb=new JpaBean();
		
		jb.setName("name");
		jb.setContent("1");
		jb.setScore(1);
		
		jpaDao.save(jb);
		
	}
	
	
	
	/**
	 * 性能测试
	 */
	@Test
	public void test2(){
		
		long sc = System.currentTimeMillis();
		
		for(int i=0;i<5000;i++){
		
			JpaBean jb=new JpaBean();
			jb.setName("name"+i);
			jb.setContent("content"+i);
			jb.setScore(i);
			jpaDao.save(jb);
		
		}
		
		System.out.println("耗时="+(System.currentTimeMillis()-sc));
		//耗时=273,623=4.5分钟
		//耗时=271,171=4.5分钟
		//单线程:18次/秒
	}
}
