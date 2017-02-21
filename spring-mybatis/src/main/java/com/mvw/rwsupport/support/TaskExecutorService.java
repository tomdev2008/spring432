package com.mvw.rwsupport.support;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 任务执行
 * 
 * @author gaotingping
 *
 * 2016年11月23日 下午5:27:40
 */
public class TaskExecutorService {

	private static final Logger logger = LoggerFactory.getLogger(TaskExecutorService.class);

	private ExecutorService executor = null;

	//线程数和队列长度可以开放
	private final static int threadSize = 8;

	public void init() {
		try {
			executor = Executors.newFixedThreadPool(threadSize);
		} catch (Exception e) {
			logger.error("Task executor init error!", e);
		}
	}

	public void destroy() {
		if (executor != null) {
			executor.shutdown();
		}
	}

	public void submit(Runnable task) {
		executor.submit(task);
	}

	public <T> Future<T> submit(Callable<T> task) {
		return executor.submit(task);
	}
}