package test.task.scheduling;

import java.util.Calendar;
import java.util.Date;

import org.junit.Test;
import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerFactory;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;

public class QuartzMinute {

	public static void main(String[] args) throws Exception {
		test();
    }
	
	@Test
	public void test13(){
		Calendar cal = Calendar.getInstance();
		System.out.println(cal.get(Calendar.MINUTE));
	}
	
	@Test
	public void test12() throws Exception{
		  //根据每天的开始毫秒数   和现在的毫秒数   计算现在的时分秒
		 //SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		 //Date d0 = sdf.parse("2015-07-29 00:00:00");
		 //System.out.println(new Date().getTime()-d0.getTime());
		long i=new Date().getTime();
		i=i/1000;
		System.out.println("h="+(i/3600));
		i=i%3600;
		System.out.println("m="+(i/60));
		i=i%60;
		System.out.println("s="+(i));
	}

	public static void test() throws Exception {
		// Scheduler 调度器
		SchedulerFactory schedFact = new org.quartz.impl.StdSchedulerFactory();
		Scheduler sched = schedFact.getScheduler();

		// job
		JobDetail job = JobBuilder.newJob(MyJob1.class)
			.withIdentity("job1", "group1")
			.usingJobData("JobName", "Hello World!")
			.build();

		// trigger
		Trigger trigger = TriggerBuilder.newTrigger()
				.withIdentity("trigger_1", "group_1")
				.startNow()
				.withSchedule(SimpleScheduleBuilder.simpleSchedule()
				.withIntervalInMinutes(1)
				.repeatForever()
		    ).build();

		sched.scheduleJob(job, trigger);

		sched.start();
	}

	@Test
	public void test1() throws Exception {
		
		SchedulerFactory schedFact = new org.quartz.impl.StdSchedulerFactory();
		Scheduler sched = schedFact.getScheduler();
		sched.start();

		JobDetail job = JobBuilder.newJob(MyJob1.class)
			.withIdentity("myJob", "group1")
			.build();

		Trigger trigger = TriggerBuilder.newTrigger()
			.withIdentity("simpleTrigger", "triggerGroup")
			.withSchedule(CronScheduleBuilder.cronSchedule("0/5 * * * * ?"))
			.startNow()
			.build();

		sched.scheduleJob(job, trigger);
		System.in.read();
	}
}
