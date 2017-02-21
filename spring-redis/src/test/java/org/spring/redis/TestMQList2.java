package org.spring.redis;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring-redis-gtp.xml" })
public class TestMQList2 {

	@Autowired
	private StringRedisTemplate template;

	private ListOperations<String, String> list = null;

	private final static String KEY = "geo_test_20161010";

	private final static long TIMEOUT = 3;

	@Before
	public void test() {
		list = template.opsForList();
	}

	@Test
	public void test1() throws IOException {

		new Thread(new Runnable() {
			@Override
			public void run() {
				for (int i = 0; i < 50; i++) {
					testAdd("v" + i);
					if(i%5==0){
						try {
							Thread.sleep(5000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}
				System.out.println("执行完成");
			}
		}).start();

		new Thread(new Runnable() {
			@Override
			public void run() {
				while(true){
					testGet();
				}
			}
		}).start();
		
		System.in.read();
	}

	// 插入头部
	public void testAdd(String val) {
		list.leftPush(KEY, val);
	}

	// 阻塞弹出尾元素
	public void testGet() {
		String tmp = list.rightPop(KEY, TIMEOUT, TimeUnit.SECONDS);
		System.out.println(tmp);
	}
}
