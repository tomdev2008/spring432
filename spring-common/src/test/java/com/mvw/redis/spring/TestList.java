package com.mvw.redis.spring;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/*
 *  看看其封装结构:RedisOperations 操作定义
	index(K, long)
	leftPop(K)
	leftPop(K, long, TimeUnit)
	leftPush(K, V)
	leftPushAll(K, V...)
	leftPushAll(K, Collection<V>)
	leftPushIfPresent(K, V)
	leftPush(K, V, V)
	size(K)
	range(K, long, long)
	remove(K, long, Object)
	rightPop(K)
	rightPop(K, long, TimeUnit)
	rightPush(K, V)
	rightPushAll(K, V...)
	rightPushAll(K, Collection<V>)
	rightPushIfPresent(K, V) //可以去里面看其实现，存在才操作
	rightPush(K, V, V)
	rightPopAndLeftPush(K, K)
	rightPopAndLeftPush(K, K, long, TimeUnit)
	set(K, long, V)
	trim(K, long, long)
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/spring-redis.xml" })
public class TestList {

	@Autowired
	private StringRedisTemplate template;
	
	private ListOperations<String, String> list=null;

	@Before
	public void test() {
		list = template.opsForList();
	}
	
	@Test //添加
	public void test1(){
		
		for(int i=0;i<10;i++){
			list.leftPush("list", "v"+i);
		}
	}
	
	@Test
	public void test2(){
		Long size = list.size("list");
		System.out.println(size);
	}
	
	@Test //包含索引，下标从0开始
	public void test3(){
		//v9 v0  [2,5] =v7-v4 
		List<String> t = list.range("list", 2, 5);
		for(String s:t){
			System.out.println(s);
		}
	}
}