package test_dao;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.mvw.mybatis.dao.MyDao;
import com.mvw.mybatis.model.MyBean;
import com.mvw.mybatis.model.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring-test.xml"})
public class TestMyDao {

	@Autowired
	private MyDao myDao;
	
	//查询
	@Test
	public void test1(){
		List<MyBean> list = myDao.findAll();
		for(MyBean mb:list){
			System.out.println(mb);
		}
	}
	
	//更新
	@Test
	public void test2(){
		Integer size = myDao.update(1, 119);
		System.out.println("size="+size);
	}
	
	//插入
	@Test
	public void test3(){
		MyBean bean=new MyBean();
		bean.setContent("内容");
		bean.setName("名称");
		bean.setScore(123);
		bean.setTime("2015-09-17");
		Integer size = myDao.add(bean);
		System.out.println("size="+size);
	}
	
	//查
	@Test
	public void test4(){
		List<MyBean> list = myDao.findByName("jpa789");
		for(MyBean mb:list){
			System.out.println(mb);
		}
	}
	
	//删
	@Test
	public void test5(){
		Integer size = myDao.del(120);
		System.out.println("size="+size);
	}
	
	@Test
	public void test6(){
		MyBean bean=new MyBean();
		bean.setId(-1);
		bean.setName("jpa789");
		List<MyBean> list = myDao.findByBean(bean);
		for(MyBean mb:list){
			System.out.println(mb);
		}
	}
	
	@Test
	public void test7(){
		List<MyBean> list = myDao.findByParams(-1, "jpa789");
		for(MyBean mb:list){
			System.out.println(mb);
		}
	}
	
	@Test
	public void test8(){
		List<MyBean> list = myDao.findByIds(112,119,120);
		for(MyBean mb:list){
			System.out.println(mb);
		}
	}
	
	//批量
	@Test
	public void test9(){
		List<MyBean> list=new ArrayList<MyBean>();
		
		for(int i=0;i<10;i++){
			MyBean bean=new MyBean();
			bean.setContent("内容");
			bean.setName("名称");
			bean.setScore(123);
			bean.setTime("2015-09-17");
			
			list.add(bean);
		}
		Integer size = myDao.addBatch(list);
		System.out.println("size="+size);
	}
	
	//联合查询
	@Test
	public void test10(){
		List<User> list = myDao.findUser();
		for(User mb:list){
			System.out.println(mb);
		}
	}
	
	@Test
	public void test11(){
		MyBean obj = myDao.findById(121);
		System.out.println(obj);
	}
	
	@Test
	public void test12(){
		//{1={id=1, name=123}, 2={id=2, name=456}}
		Map<?, ?> obj = myDao.getMap();
		System.out.println(obj);
	}
	
	@Test
	public void test121(){
		Map<Integer,User> obj = myDao.getMap2();
		System.out.println(obj);
	}
	
	@Test
	public void test122(){
		Map<Integer,List<User>> obj = myDao.getMap3();
		System.out.println(obj);
	}
	
	@Test
	public void test13(){
		test1();
		test2();
		test3();
		test4();
	}
	
	@Test
	public void test14(){
		
		long sc = System.currentTimeMillis();
		
		for(int i=0;i<5000;i++){
		
			MyBean jb=new MyBean();
			jb.setName("name"+i);
			jb.setContent("content"+i);
			jb.setScore(i);
			jb.setTime("2015-09-18");
			
			myDao.add(jb);
		}
		
		System.out.println("耗时="+(System.currentTimeMillis()-sc));
		//耗时=257,969=
	    //耗时=257,077=
		//单线程:19次/秒
	}
	
	@Test
	public void test15(){
		List<String> list = myDao.findAllName("_id");
		System.out.println(list);
	}
	
	
	@Test
	public void test17(){
		System.out.println("%%".replace("%", "\\%"));
	}
	
	//sql注入
	@Test
	public void test16(){
		 List<MyBean> r = myDao.sql_injection("1' OR '1'='1");
		 System.out.println(r);
		 
		 r = myDao.sql_injection2("名\\_");
		 System.out.println(r);
	}
	
	@Test
	public void test18(){
		 List<MyBean> r = myDao.sql_injection3("' or 1=1'");
		 System.out.println(r);
	}
}
