package org.spring.jedis;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisSentinelPool;

public class TestSentinel {

	@Test
	public void test1(){
	
		Set<String> sentinels = new HashSet<String>();
    	sentinels.add("192.168.0.151:26383");
    	
    	JedisPoolConfig config = new JedisPoolConfig();
		config.setMaxTotal(50);// 最大
		config.setMaxIdle(5);// 最大空闲
		config.setMaxWaitMillis(5 * 1000);// 最大等待时间
    	
    	JedisSentinelPool pool = new JedisSentinelPool("master1", sentinels,config);
    	
    	Jedis jedis = pool.getResource();

    	System.out.println(jedis.info());
    	
    	jedis.close();
    	pool.close();
	}
}
