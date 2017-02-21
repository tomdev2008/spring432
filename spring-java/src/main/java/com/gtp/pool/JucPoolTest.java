package com.gtp.pool;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.junit.Test;

/**
 * 经验
 * 1.线程必须有限制:不然大并发压死机器
 * 2.队列长度建议限制:堆积太高也不好，并且宕机影响(任务入库，抢占执行)
 * 3.添加任务等以及任务要捕获异常(异常流的处理)
 * 
 * @author gaotingping
 *
 * 2016年11月25日 上午10:54:41
 */
public class JucPoolTest {

	@Test
	public void test1() {

		ExecutorService executor = Executors.newFixedThreadPool(3);

		executor.submit(new Runnable() {

			@Override
			public void run() {

			}

		});

		executor.submit(new Callable<String>() {

			@Override
			public String call() throws Exception {
				return null;
			}

		});

		if (executor != null) {
			executor.shutdown();
		}
	}
}
