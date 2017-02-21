package jadetest;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import com.mvw.jadetest.dao.UserDAO;
import com.mvw.jadetest.model.Person;


@ContextConfiguration(locations = { "classpath:spring-db.xml"})
public class CRUDTest1 extends AbstractJUnit4SpringContextTests {
	
	@Autowired
	private UserDAO userDAO;
	
	@Test
	public void test(){
		System.out.println(userDAO.getAll());
	}
	
	@Test
	public void test2(){
		for(int i=0;i<10;i++){
			Person p=new Person();
			p.setName("name"+i);
			userDAO.save(p);
		}
	}
	
	@Test
	public void test3(){
		Integer len = userDAO.up("name_1", 1);
		System.out.println(len);
	}
}
