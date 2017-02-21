package test.task.scheduling;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 相当于给设置了多个守护线程
 * 适合简单的，循环执行这样的任务
 * 
 * 支持多个任务并发执行,是对timer的简单替代
 * 但是注意，抛出异常对整个执行流程的影响
 * 
 * @author <a href="mailto:tingping@ssreader.cn">tingping</a>
 * @Version 2014-5-30
 */
public class MyScheduledExecutor {
	
	public static void main(String[] args) {
		
		 ScheduledExecutorService service = Executors.newScheduledThreadPool(3);
		 
		 long initialDelay1 = 1;
		 long period1 = 1;
		
		 /*
		  * 按指定频率周期执行某个任务
		  * 注意:
		  * 	实当执行任务的时间大于我们指定的间隔时间时，它并不会在指定间隔时开辟一个新的线程并发执行这个任务。而是等待该线程执行完毕
		  * 	需要捕获最上层的异常，防止出现异常中止执行，导致周期性的任务不再执行
		  */
		//从现在开始1秒钟之后，每隔1秒钟执行一次job1
		service.scheduleAtFixedRate(new ScheduledExecutorTest(), initialDelay1,period1, TimeUnit.SECONDS);
		
		 long initialDelay2 = 1;
		 long delay2 = 1;
		 //按指定频率间隔执行某个任务
		// 从现在开始2秒钟之后，每隔2秒钟执行一次job2
		service.scheduleWithFixedDelay(new ScheduledExecutorTest(), initialDelay2,delay2, TimeUnit.SECONDS);
	}
}

class ScheduledExecutorTest implements Runnable{
	public void run() {
		System.out.println("ScheduledExecutorTest execute");
	}
}
