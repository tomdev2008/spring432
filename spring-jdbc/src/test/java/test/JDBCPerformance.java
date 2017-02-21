package test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.mvw.cpm.dao.T1Dao;

import model.MyModel;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring-servlet.xml" })
public class JDBCPerformance {

	@Autowired
	private T1Dao dao;
	
	/**
	 * 性能测试
	 */
	@Test
	public void test2(){
		
		for(int i=0;i<5;i++){
			
			MyModel jb=new MyModel();
			jb.setName("name"+i);
			jb.setContent("content"+i);
			jb.setScore(i);
			jb.setTime("2015-09-18");
			dao.test(jb);
		
		}
		
		long sc = System.currentTimeMillis();
		
		for(int i=0;i<5000;i++){
		
			MyModel jb=new MyModel();
			jb.setName("name"+i);
			jb.setContent("content"+i);
			jb.setScore(i);
			jb.setTime("2015-09-18");
			dao.test(jb);
		
		}
		
		System.out.println("耗时="+(System.currentTimeMillis()-sc));
		//耗时=255271
		//耗时=257234
		//    315032
		//19次/秒
	}
}
