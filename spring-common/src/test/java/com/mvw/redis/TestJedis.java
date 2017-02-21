package com.mvw.redis;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisMonitor;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisPubSub;
import redis.clients.jedis.Pipeline;
import redis.clients.jedis.Response;
import redis.clients.jedis.Transaction;

//基于3.0 快照和aof一起使用，配置参考本目录下文件
public class TestJedis {
	
	private Jedis jedis = null;

	@Before
	public void init() {
		jedis = new Jedis("127.0.0.1");
		jedis.select(0);
	}

	@After
	public void close() {
		if (jedis != null) {
			jedis.disconnect();
		}
	}
	
	@Test
	public void test5(){
		jedis.set("123", "9BA5DCB342F512AF2CC6DBD0A3CABAEB");
		String s = jedis.get("123");
		System.out.println(s);
		
		System.out.println(jedis.info());
	}


	@Test
	public void test1() {
		for (int i = 0; i < 100; i++) {
			jedis.set("foo" + i, "bar" + i);
		}
	}

	@Test
	public void test8() {
		// 手动主从
		// jedis.slaveOf("192.168.1.35", 6379);
		// slave1jedis.slaveofNoOne();
	}

	// JedisPool,这是一个线程安全的网络连接池
	@Test
	public void test7() {
		JedisPool pool = null;
		if (pool == null) {
			JedisPoolConfig config = new JedisPoolConfig();
			
			config.setMaxTotal(50);// 最大
			config.setMaxIdle(5);// 最大空闲
			config.setMaxWaitMillis(5 * 1000);// 最大等待时间
			config.setTestWhileIdle(true);//空闲的时候检查连接可用性
			config.setTimeBetweenEvictionRunsMillis(30000);//TestWhileIdle两次扫描之间要sleep的毫秒数
			config.setMinEvictableIdleTimeMillis(60000);//一个对象至少停留在idle状态的最短时间
			config.setNumTestsPerEvictionRun(-1);//每次扫描的最多的对象数

			pool = new JedisPool(config, "192.168.8.242", 6379, 10000, "admin", 0);
		}
		
		System.out.println(pool);

		//会抛出下面异常,当连接池满了后
		//redis.clients.jedis.exceptions.JedisConnectionException: Could not get a resource from the pool

		Jedis redis = pool.getResource();
		
		System.out.println(redis.get("t1"));
		
		redis.close();//归还连接
		
		pool.destroy();

		 //模版代码
		 Jedis jedis = null;
		 try {
			 jedis = pool.getResource();
			 //do something
			 
		 }catch(Exception e){
			 e.printStackTrace();
		 } finally {
			 if (jedis != null) {
				 jedis.close();
			 }
		 }
	}
	
     //测试超时
	public static void main(String[] args) {
		JedisPool pool = null;
		if (pool == null) {
			JedisPoolConfig config = new JedisPoolConfig();
			pool = new JedisPool(config, "127.0.0.1", 6379, 3000);
		}
		System.out.println(pool);

		//会抛出下面异常,当连接池满了后
		//redis.clients.jedis.exceptions.JedisConnectionException: Could not get a resource from the pool

		Jedis redis = pool.getResource();
		
		try {
			Thread.sleep(30000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		System.out.println(redis.get("t1"));
		
		redis.close();//归还连接
		
		pool.destroy();
	}

	// monitor
	@Test
	public void test6() {
		/*
		 * 单示例非线程安全 如如下的例子
		 * 这里使用同一个连接 会阻塞 read time out
		 */
		new Thread(new Runnable() {

			public void run() {
				Jedis j = jedis;
				for (int i = 0; i < 100; i++) {
					j.set("key" + i, "value" + i);
				}
				j.disconnect();
			}
		}).start();
		
		jedis.monitor(new JedisMonitor() {
			public void onCommand(String command) {
				System.out.println(command);
			}
		});
	}

	// Pub/Sub
	@Test
	public void test4() {
		try {
			// 单线程不安全
			new Thread(new Runnable() {
				public void run() {
					Jedis j = new Jedis("127.0.0.1");
					System.out.println(j);
					try {
						for (int i = 0; i < 100; i++) {
							Thread.sleep(1000);
							j.publish("foo", "message+" + i);
						}
					} catch (InterruptedException e) {
						e.printStackTrace();
					} finally {
						j.disconnect();
					}
				}
			}).start();

			Thread.sleep(3000);

			// 订阅是一个阻塞操作 会一直监控等待中
			MyListener l = new MyListener();

			// 写上自己的监听器和订阅频道
			jedis.subscribe(l, "foo");

		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	// Pipelining(适合多个短命令)
	@Test
	public void test3() {
		Pipeline p = jedis.pipelined();
		p.set("fool3", "bar");
		p.set("foo4", "barowitch");
		p.set("foo5", "barinsky");
		p.set("foo6", "barikoviev");
		Response<String> res = p.get("foo6");
		p.sync();
		System.out.println(res.get());
	}

	// transactions
	@Test
	public void test2() {
		Transaction t = jedis.multi();
		t.set("t1", "bar1");
		//int i=1/0;
		//System.out.println(jedis.get("t1")); //不能在同一个事务内再get
		t.set("t2", "bar2");
		t.exec();
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
	public void test81(){
		
	}
	
	
	@Test
	public void commandTest(){
	   //jedis.del
	  //jedis.dump(key);
	}
}

//发布与订阅事件监听器
class MyListener extends JedisPubSub {

	// 接收到消息
	public void onMessage(String channel, String message) {
		System.out.println(channel + "=" + message);
	}

	// 订阅
	public void onSubscribe(String channel, int subscribedChannels) {
		System.out.println(2);
	}

	// 取消订阅
	public void onUnsubscribe(String channel, int subscribedChannels) {
		System.out.println(3);
	}

	// 正则模糊订阅
	public void onPSubscribe(String pattern, int subscribedChannels) {
		System.out.println(4);
	}

	// 取消
	public void onPUnsubscribe(String pattern, int subscribedChannels) {
		System.out.println(5);
	}

	// 接收到消息
	public void onPMessage(String pattern, String channel, String message) {
		System.out.println(6);
	}
}
