package com.gtp.pool.test_future;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

import org.junit.Test;

/**
 * 特别常用的一种多线程设计模式:让主线程等待时间释放，可以做其它事情
 * 
 * @author gaotingping
 *
 * 2017年2月3日 上午11:00:07
 */
public class TestFuture {

	/*
	 * JDK实现
	 * java.util.concurrent.FutureTask
	 */
	@Test
	public void test1() throws Exception{
		
		FutureTask<String> futureTask = new FutureTask<String>(new RealData2("name"));
		
		ExecutorService executor = Executors.newFixedThreadPool(1); //使用线程池
		
		//执行FutureTask，相当于上例中的client.request("name")发送请求
		Future<?> f = executor.submit(futureTask);
		//这里可以用一个sleep代替对其他业务逻辑的处理
		//在处理这些业务逻辑过程中，RealData也正在创建，从而充分了利用等待时间
		Thread.sleep(2000);
		
		//使用真实数据
		//如果call()没有执行完成依然会等待
		System.out.println("数据=" + futureTask.get());
		System.out.println(f.get());
	}
	
	@Test
	public void test2() throws Exception{
		MyFutureTask client = new MyFutureTask();
		//这里会立即返回，因为获取的是FutureData，而非RealData
		MyData data = client.request("name");
		//这里可以用一个sleep代替对其他业务逻辑的处理
		//在处理这些业务逻辑过程中，RealData也正在创建，从而充分了利用等待时间
		//Thread.sleep(2000);
		System.out.println("立即返回");
		//使用真实数据
		System.out.println("数据="+data.getResult());
		System.out.println("执行结束");
	}
}
