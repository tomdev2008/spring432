package test_dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.mvw.mybatis.dao.SqlDao;
import com.mvw.mybatis.model.MyBean;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring-test.xml"})
public class TestSqlDao {

	@Autowired
	private SqlDao sqlDao;
	
	@Test
	public void test1(){
		MyBean t = sqlDao.getUserById(5);
		System.out.println(t);
	}
}
