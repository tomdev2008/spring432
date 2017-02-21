package org.spring.mongodb;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;


/**
 * 单元测试
 */
@ContextConfiguration(locations = { "classpath:spring-mongodb.xml" })
public class Test1 extends AbstractJUnit4SpringContextTests {
	
	@Autowired
	private  MongoTemplate mongoTemplate;
	
	
	/**
	 * 
	id类型: BigInteger,String,ObjectID,其它类型报如下异常：
	
	org.springframework.dao.InvalidDataAccessApiUsageException: Cannot autogenerate id of type java.lang.Object 
	for entity of type com.mvw.cpm.model.Person!
	at org.springframework.data.mongodb.core.MongoTemplate.assertUpdateableIdIfNotSet(MongoTemplate.java:1149)
	at org.springframework.data.mongodb.core.MongoTemplate.doInsert(MongoTemplate.java:708)
	at org.springframework.data.mongodb.core.MongoTemplate.insert(MongoTemplate.java:672)
	at org.springframework.data.mongodb.core.MongoTemplate.insert(MongoTemplate.java:663)
	 */
	@Test
	public void test1(){
		Person p=new Person();
		//p.setId(1);
		p.setAge(11);
		p.setName("name11");
		mongoTemplate.save(p);
	}
	
	
	@Test
	public void test2(){
		Query query = new Query();
		Criteria criteria = Criteria.where("name").is("Joe");
		query.addCriteria(criteria);
		List<Person> list = mongoTemplate.find(query, Person.class);
		System.out.println(list);
		
	}
	
	@Test
	public void test3(){
		
		//这是最优问题的，数值类型的记录存在的，但是它查询不到
		Person p = mongoTemplate.findById("5600f6407c1f608610ba7065", Person.class);
		System.out.println("p="+p);
	}
	
	//测试单线程插入
	@Test
	public void test4(){
		
		long sc = System.currentTimeMillis();
		
		for(int i=0;i<500;i++){
		
			Person p=new Person();
			p.setAge(i);
			p.setName("name_"+i);
			mongoTemplate.save(p);
		
		}
		
		System.out.println("耗时="+(System.currentTimeMillis()-sc));
		//218毫秒 190 192  209 206 206 201
	}
}
