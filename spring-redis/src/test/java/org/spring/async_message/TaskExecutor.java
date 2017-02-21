package org.spring.async_message;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 任务执行器 调度器只负责协调调度，任务的执行交给任务执行器完成
 * 
 * 1.线程数量得限制
   2.队列长度得限制，防止堆积?满了如何处理？
   3.异常处理和重试恢复等
   4.异常宕机考虑(断电，kill -9)
   5.指定心跳内，采取执行器的执行情况(toString的信息就够用了)
   
         任务列表还是入库，多机器抢任务执行(MQ通知或循环扫描)
         这种资源的处理，最好单独拿出来
 * 
 * @author gaotingping
 *  2016年9月23日 下午1:36:26
 */
public class TaskExecutor {

	private ExecutorService executor = null;

	private final static int THREADSIZE = 8;
	
	private final static int QUEUESIZE = 5000;

	public void init() {
		try {
			executor = new ThreadPoolExecutor(THREADSIZE, THREADSIZE,
                    0L, TimeUnit.MILLISECONDS,
                    new ArrayBlockingQueue<Runnable>(QUEUESIZE));
			/*
			 * return new ThreadPoolExecutor(nThreads, nThreads,
                                      0L, TimeUnit.MILLISECONDS,
                                      new LinkedBlockingQueue<Runnable>()
			 */
		} catch (Exception e) {
			e.printStackTrace();
		}
	}//方法的职责单一原则

	public void destroy() {
		if (executor != null) {
			//阻止再提交，已接收任务执行完毕
			executor.shutdown();
		}
	}

	//添加任务
	public void submit(Runnable task) {
		executor.submit(task);
	}

	//添加任务：有返回值，还可以有超时处理 return future.get(this.timeout,TimeUnit.MILLISECONDS);
	public <T> Future<T> submit(Callable<T> task) {
		return executor.submit(task);
	}
}
