package test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.mvw.cpm.dao.T1DaoImpl;

import model.MyModel;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring-servlet.xml" })
public class T1DaoImplTest {

	@Autowired
	private T1DaoImpl dao;
	
	/**
	 * 性能测试
	 * @throws Exception 
	 */
	@Test
	public void test1() throws Exception{
		
		MyModel tt = dao.getById("select * from t1 where _id=?", 1);
		System.out.println(tt);
	}
}
