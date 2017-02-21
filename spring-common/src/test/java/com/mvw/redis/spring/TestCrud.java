package com.mvw.redis.spring;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/*
 * 看看其封装结构:RedisOperations 操作定义
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/spring-redis.xml" })
public class TestCrud {

	@Autowired
	private StringRedisTemplate template;

	@Test
	public void test() {
		template.multi();
		Long tmp = template.getExpire("123");
		System.out.println(tmp);
		template.exec();
	}

	@Test
	public void test1() {
		template.multi();
		template.opsForValue().set("1234", "456");
		String rr = template.opsForValue().get("1234");// null 因为还没有提交呢
		System.out.println(rr);
		template.delete("1234");
		template.exec();
	}

	@Test
	public void test2() {
		template.multi();
		template.opsForValue().set("1234", "456");
		String rr = template.opsForValue().get("1234");// null 因为还没有提交呢
		System.out.println(rr);
		template.delete("1234");
		template.exec();
	}

	@Test
	public void test3() {
		// template.opsForXxx;
	}
}