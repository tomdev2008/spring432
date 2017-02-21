package com.mvw.redis;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.Jedis;

/**
 * key-value缓存实现
 * 
 * @author gaotingping
 *
 *         2016年8月4日 下午2:03:43
 */
public class CacheRedisService {

	protected RedisFactory redisFactory;

	private String dbName = "common";

	private static Logger logger = LoggerFactory.getLogger(CacheRedisService.class);

	protected void setDbName(String dbName) {
		this.dbName = dbName;
	}

	protected Jedis getSource() {
		return redisFactory.getResource(dbName);
	}

	protected void returnSource(Jedis jedis) {
		redisFactory.returnResource(jedis);
	}

	public String getValue(String key) {
		Jedis redis = null;
		try {
			redis = getSource();
			if (redis != null) {
				return redis.get(key);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			returnSource(redis);
		}
		return null;
	}

	public void setValue(String key, String value, int seconds) {
		Jedis redis = null;
		try {
			redis = getSource();
			redis.set(key, value);
			if (seconds > 0) {/* 严格情况下 下面这个应该加事务 */
				redis.expire(key, seconds);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			returnSource(redis);
		}
	}

	public void delValue(String key) {
		Jedis redis = null;
		try {
			if (!StringUtils.isEmpty(key)) {
				redis = getSource();
				redis.del(key);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			returnSource(redis);
		}
	}

	public Long getDBSzie() {
		Jedis redis = null;
		try {
			redis = getSource();
			return redis.dbSize();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			returnSource(redis);
		}
		return null;
	}

	public RedisFactory getRedisFactory() {
		return redisFactory;
	}

	public void setRedisFactory(RedisFactory redisFactory) {
		this.redisFactory = redisFactory;
	}
}
