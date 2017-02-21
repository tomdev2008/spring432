package jadetest;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import com.mvw.jadetest.dao.SubTabDAO;


@ContextConfiguration(locations = { "classpath:spring-servlet.xml"})
public class SubTableTest1 extends AbstractJUnit4SpringContextTests {
	
	@Autowired
	private SubTabDAO subTabDAO;
	
	@Test //查询
	public void test1(){
		System.out.println(subTabDAO.get(3));
	}
	
	@Test //插入
	public void test2(){
		for(int i=0;i<10;i++){
			subTabDAO.save("name_"+i, i);
		}
	}
	
	@Test //更新
	public void test3(){
		subTabDAO.up("name_333", 3);
	}
}
