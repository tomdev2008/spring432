package com.mvw.redis;

import redis.clients.jedis.Jedis;

/**
 * 资源持有者  
 * 让资源使用者不依赖资源提供者
 * 
 * @author gaotingping
 *
 * 2016年8月4日 下午3:08:05
 */
public class RedisSourceHolder {

	private RedisFactory redisFactory;
	
	public Jedis getSource(String name){
		return redisFactory.getResource(name);
	}
	
	public void returnSource(Jedis jedis){
		redisFactory.returnResource(jedis);
	}

	public RedisFactory getRedisFactory() {
		return redisFactory;
	}

	public void setRedisFactory(RedisFactory redisFactory) {
		this.redisFactory = redisFactory;
	}
}
