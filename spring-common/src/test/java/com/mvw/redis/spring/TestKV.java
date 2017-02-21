package com.mvw.redis.spring;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

//只能去迎合它了
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/spring-redis.xml" })
public class TestKV {

	@Autowired
	private StringRedisTemplate t;
	
	private ValueOperations<String, String> kv=null;

	@Before
	public void test() {
		kv = t.opsForValue();
	}
	
	@Test
	public void test1(){
		
		//关于常用的key的操作
		//kv.get(key);
		//kv.set(key, value);
		//t.expire(key, timeout, unit);
		//t.delete(key);
		//t.getExpire(key);
		
		System.out.println(kv);
	}
}