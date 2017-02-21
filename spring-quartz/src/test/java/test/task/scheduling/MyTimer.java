package test.task.scheduling;

import java.util.Timer;
import java.util.TimerTask;

/**
    //特别简单，时候入门级别学习使用,实践用处不大

	schedule(TimerTask task, Date time) 
          	安排在指定的时间执行指定的任务。 
          	
 	schedule(TimerTask task, Date firstTime, long period) 
          	安排指定的任务在指定的时间开始进行重复的固定延迟执行。 
          	
	schedule(TimerTask task, long delay) 
          	安排在指定延迟后执行指定的任务。 
          	
	schedule(TimerTask task, long delay, long period) 
          	安排指定的任务从指定的延迟后开始进行重复的固定延迟执行。 
       
     说明:
	    计时器任务应该迅速完成。如果完成某个计时器任务的时间太长
	    那么它会“独占”计时器的任务执行线程。因此，这就可能延迟后
	    续任务的执行，而这些任务就可能“堆在一起”，并且在上述不友
	    好的任务最终完成时才能够被快速连续地执行(单线程执行的)
	    
	     即：此类无法提供实时保证：它使用 Object.wait(long) 方法来安排任务
	  
	 综上所述：它适合单任务，简短任务的执行处理上！比如说每月日志表的维护等。
	                    是一个初级的定时器实现。
 */
public class MyTimer {
	public static void main(String[] args) {
		Timer tt=new Timer();
		
		tt.schedule(new TimerTask() {
			public void run() {
				System.out.println("我一秒执行一次!");
				
			}},1000,1000);
		
		tt.schedule(new TimerTask() {
			public void run() {
				System.out.println("我二秒执行一次!");
				
			}},1000,2000);
	}
}