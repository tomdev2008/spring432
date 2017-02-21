package org.spring.jedis;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import redis.clients.jedis.Jedis;

public class RedisTest{

	private Jedis jedis = null;

	@Before
	public void init() {
		jedis = new Jedis("127.0.0.1");
		//jedis.auth("admin");
		jedis.select(1);
	}

	@After
	public void close() {
		if (jedis != null) {
			jedis.disconnect();
		}
	}
	
	@Test
	public void testA1(){
		System.out.println(jedis);
		//jedis.set("k123", "v123");
		System.out.println(jedis.get("k123"));
	}
	
	//典型应用:频率限制
	@Test
	public void test(){
		while(true){
			//这里应该加事务
			Long len = jedis.incr("k001");
			if(len==0){
				jedis.expire("k001", 10);
			}else if(len>10){
				System.out.println("每分钟最多访问10次");
				break;
			}
			System.out.println(len);
		}
	}
	
	@Test
	public void test1(){
		jedis.sadd("kkk", "k1","k1");
	}
}
