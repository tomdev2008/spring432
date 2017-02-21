package com.mvw.redis;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.configuration.XMLConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * redis连接管理
 * 
 * @author gaotingping
 *
 * 2016年8月4日 下午1:51:24
 */
public class DefaultRedisFactory implements RedisFactory{

	private static Map<String, JedisPool> map = null;

	private static Logger logger = LoggerFactory.getLogger(DefaultRedisFactory.class);

	private final static String PATH = "redis-config.xml";

	private static XMLConfiguration xml = null;
	
	public void init() {
		try {
			map = new HashMap<String, JedisPool>();
			xml = new XMLConfiguration(PATH);
			int index = 0;
			while (true) {
				index++;
				String db = "db" + index;
				String name = xml.getString(db + ".name");
				if (name == null) {
					break;
				} else {
					JedisPoolConfig config = new JedisPoolConfig();
					config.setMaxTotal(xml.getInt(db + ".maxtotal"));
					config.setMaxIdle(xml.getInt(db + ".maxidle"));
					config.setMaxWaitMillis(xml.getInt(db + ".maxwait"));
					JedisPool pool = new JedisPool(config, 
						xml.getString(db + ".host"), 
						xml.getInt(db + ".port"),
						xml.getInt(db + ".timeout"), 
						xml.getString(db + ".pwd"), 
						xml.getInt(db+".id"));
					map.put(name, pool);
				}
			}
			logger.info("JedisPool init success");
		} catch (Exception e) {
			logger.error("JedisPool init error", e);
		}
	}

	public void destroy() {
		try {
			if (map != null) {
				Collection<JedisPool> jtps = map.values();
				if (jtps != null) {
					for (JedisPool jtp : jtps) {
						jtp.destroy();
					}
					logger.info("JedisPool close");
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

	/**
	 * 获得jedis
	 * 
	 * @param 	dbName  配置文件中指定的名称，dbName与索引库一一对应
	 * @return  失败返回null
	 * 
	 * @throws RuntimeException
	 */
	public Jedis getResource(String dbName){
		Jedis redis = null;
		try {
			if (map != null) {
				JedisPool pool = map.get(dbName);
				if (pool != null) {
					redis = pool.getResource();
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return redis;
	}

	/**
	 * 归还jedis
	 * 
	 * @param jedis
	 */
	public void returnResource(Jedis jedis) {
	    if(jedis!=null){
	    	jedis.close();
	    }
    }
}
