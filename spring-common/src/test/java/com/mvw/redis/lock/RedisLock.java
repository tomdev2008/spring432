package com.mvw.redis.lock;

import java.util.Date;

import org.junit.Test;

import redis.clients.jedis.Jedis;

/**
 * 两个棘手问题:
 * 1.锁超时
 * 2.死锁
 * 
 * 算法:
 * # 	get lock
		lock = 0
		while lock != 1:
		    timestamp = current Unix time + lock timeout + 1
		    lock = SETNX lock.foo timestamp
		    if lock == 1 or (now() > (GET lock.foo) and now() > (GETSET lock.foo timestamp)):
		        break;
		    else:
		        sleep(10ms)
		
		# do your job
		do_job()
		
		# release
		if now() < GET lock.foo:
		    DEL lock.foo 
 * 
 * @author gaotingping
 *
 * 2016年8月10日 下午3:00:14
 */
public class RedisLock {

	// 要开启锁得 3*O(1) --分布式锁
	@Test
	public void testAAA(){
		test2("tr_key",300000);
	}
	
	/**
	 * 
	 * @param key
	 * @param timeOut  超时时间，单位未毫秒
	 */
	public void test2(String key, final long timeOut) {

		long timeOutStr = new Date().getTime() + timeOut;

		// lock
		try {
			if (lock(key, timeOutStr + "")) {
				System.out.println("ok");
			} else {
				System.out.println("获取事务失败!");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			long e = System.currentTimeMillis();
			if (timeOut > e) {// 是否过期,未过期释放资源
				uNlock(key);
			}
		}
	}
	
	
	@Test
	public void testSleep(){
		long s = System.currentTimeMillis();
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println((System.currentTimeMillis()-s));
	}

	private void uNlock(String key) {
		Jedis j = new Jedis("127.0.0.1");
		j.select(1);

		try {
			j.del(key);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			j.close();
		}
	}

	/**
	 * 获得分布式锁
	 * 
	 * 问题1:正常业务超时如何处理？还是会产生脏数据
	 * 
	 * @param key  
	 * @param plan_expire_time  期望超时时间，不同的业务模块有不同的期望，单位毫秒 ms  这里开放  默认3s  
	 * @return
	 */
	private boolean lock(String key, String v) {

		Jedis j = new Jedis("127.0.0.1");
		j.select(1);

		try {
			Long s = j.setnx(key, v);
			if (s == 1) {/* 空闲 */
				return true;
			} else {
				String oldExpireTime = j.get(key);
				if (Long.parseLong(oldExpireTime) < new Date().getTime()) {/* 过期 */
					String currentExpireTime = j.getSet(key, v);
					if (oldExpireTime.equals(currentExpireTime)) {
						return true;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			j.close();
		}

		return false;
	}
	
	
	private int timeoutMsecs=1000;
	private long expireMsecs=1L;
	
	@SuppressWarnings("unused")
	private boolean locked=false;
	
	private String lockKey="";
	
	public synchronized boolean acquire(Jedis jedis) throws InterruptedException {
		int timeout = timeoutMsecs;
		while (timeout >= 0) {
			long expires = System.currentTimeMillis() + expireMsecs + 1;
			String expiresStr = String.valueOf(expires);

			if (jedis.setnx(lockKey, expiresStr) == 1) {
				locked = true;
				return true;
			}

			String currentValueStr = jedis.get(lockKey);
			if (currentValueStr != null && Long.parseLong(currentValueStr) < System.currentTimeMillis()) {
				String oldValueStr = jedis.getSet(lockKey, expiresStr);
				if (oldValueStr != null && oldValueStr.equals(currentValueStr)) {
					locked = true;
					return true;
				}
			}
			
			timeout -= 100;
			Thread.sleep(100);
		}
		return false;
	}
}
