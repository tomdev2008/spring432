package com.mvw.redis;

import org.junit.Test;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;

/**
 *  pub/sub命令整理

	#PSUBSCRIBE pattern [pattern ...]
	订阅一个或多个符合给定模式的频道
	  
	#PUNSUBSCRIBE [pattern [pattern ...]]
	指示客户端退订所有给定模式
	    
	#PUBLISH channel message
	将信息 message 发送到指定的频道 channel
	     
	#SUBSCRIBE channel [channel ...]
	订阅给定的一个或多个频道的信息
	    
	#UNSUBSCRIBE [channel [channel ...]]
	指示客户端退订给定的频道
	
	#PUBSUB <subcommand> [argument [argument ...]]
	PUBSUB 是一个查看订阅与发布系统状态的内省命令， 它由数个不同格式的子
	命令组成， 以下将分别对这些子命令进行介绍
   
 * @author gaotingping
 *
 * 2016年8月2日 下午4:51:24
 */
public class TestPubSub {
	
	public Jedis getJedis() {
		Jedis jedis = new Jedis("127.0.0.1");
		jedis.select(0);
		return jedis;
	}

	@Test
	public void test4() {
		try {
			toPubData();
			Thread.sleep(3000);
			
			// 写上自己的监听器和订阅频道
			Jedis j = getJedis();
			j.subscribe(new JedisPubSub(){
				public void onMessage(String channel, String message) {
					System.out.println(channel + "=" + message);
				}
			}, "foo");

		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	
	public void toPubData(){
		new Thread(new Runnable() {
			public void run() {
				Jedis j = getJedis();
				try {
					for (int i = 0; i < 50; i++) {
						Thread.sleep(3000);
						/*向指定频道发送数据*/
						j.publish("foo", "message+" + i);
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				} finally {
					j.disconnect();
				}
			}
		}).start();
	}
}
