package com.mvw.redis;

import redis.clients.jedis.Jedis;

/**
 * redis连接管理
 * 
 * @author gaotingping
 *
 * 2016年8月4日 下午1:51:24
 */
public interface RedisFactory {

	/**
	 * 获得jedis
	 * 
	 * @param 	dbName  配置文件中指定的名称，dbName与索引库一一对应
	 * @return  失败返回null
	 * 
	 * @throws RuntimeException
	 */
	public Jedis getResource(String dbName);

	/**
	 * 归还jedis
	 * 
	 * @param jedis
	 */
	public void returnResource(Jedis jedis);
}
