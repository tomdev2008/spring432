package org.spring.mongodb;

import org.bson.types.ObjectId;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;


/**
 * 单元测试
 */
@ContextConfiguration(locations = { "classpath:spring-mongodb.xml" })
public class Test2 extends AbstractJUnit4SpringContextTests {
	
	@Autowired
	private M2Dao m2Dao;
	
	@Test
	public void test1(){
		
		//单线程啊，这就奇高了
		//2小时          tps:1.3W/s
		//500560  9分钟       tps:1.8W/s
		long sc = System.currentTimeMillis();
		
		for(int i=0;i<50;i++){
		
			JpaBean jb=new JpaBean();
			jb.setName("文件集在内部被切分成多个数据域name"+i);
			jb.setContent("每个集合都会有自己独立的命名空间content"+i);
			jb.setScore(i);
			jb.setTime("2015-09-18");
			
			m2Dao.save(jb);
		
		}
		
		System.out.println("耗时="+(System.currentTimeMillis()-sc));
		
		//6713986=2小时
	}
	
	
	@Test
	public void test2(){
		//142
		long sc = System.currentTimeMillis();
		m2Dao.findOne(new ObjectId("5763b3084b94e9244c36e929"));
		System.out.println("耗时="+(System.currentTimeMillis()-sc));
	}
	
	@Test
	public void test3(){
		//608533=10分钟
		//36289=36秒  后面就是5424=5秒  主要是加载到内存了
		long sc = System.currentTimeMillis();
		m2Dao.findByName("文件集在内部被切分成多个数据域name123456");
		System.out.println("耗时="+(System.currentTimeMillis()-sc));
	}

}
