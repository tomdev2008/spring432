package test.task.scheduling;

import org.junit.Test;
import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerFactory;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;

//总的来说，满足一般业务应该没有问题的
public class QuartzTest {

	public static void main(String[] args) throws Exception {
		test2();
	}
	
	/*
	 * 有的时候你需要将状态信息保存下来
	 * 其试下方式有：JobStore
	 * RAM/JDBC/Terracotta
	 */
	
	//TriggerListener 
	public static void test4() throws Exception {
		//TriggerListeners
		//JobListeners
		//SchedulerListener 
		/*
		 * 添加到调度器
			Adding a JobListener that is interested in a particular job:
			scheduler.getListenerManager().addJobListener(myJobListener, 
			KeyMatcher.jobKeyEquals(new JobKey("myJobName", "myJobGroup")));
			You may want to use static imports for the matcher and key classes, which will make your defining the matchers cleaner:
			import static org.quartz.JobKey.*;  
			import static org.quartz.impl.matchers.KeyMatcher.*;  
			import static org.quartz.impl.matchers.GroupMatcher.*;  
			import static org.quartz.impl.matchers.AndMatcher.*;  
			import static org.quartz.impl.matchers.OrMatcher.*;  
			import static org.quartz.impl.matchers.EverythingMatcher.*;  
			
			Adding a JobListener that is interested in all jobs of a particular group
			scheduler.getListenerManager().addJobListener(myJobListener, 
			jobGroupEquals("myJobGroup"));
			Adding a JobListener that is interested in all jobs of two particular groups
			scheduler.getListenerManager().addJobListener(myJobListener, 
			or(jobGroupEquals("myJobGroup"), jobGroupEquals("yourGroup")));
			Adding a JobListener that is interested in all jobs
			scheduler.getListenerManager().addJobListener(myJobListener, 
			allJobs());
			
			//添加或删除监听器
			scheduler.getListenerManager().addSchedulerListener(mySchedListener);
			scheduler.getListenerManager().removeSchedulerListener(mySchedListener);
		 */
	}

	// 关键接口
	public static void test3() throws Exception {
		/**
			The key interfaces of the Quartz API are:
			*Scheduler - the main API for interacting with the Scheduler.
			*Job - an interface to be implemented by components that you want the Scheduler to execute.
			*JobDetail - used to define instances of Jobs.
			*Trigger - a component that defines the schedule upon which a given Job will be executed.
			*JobBuilder - used to define/build JobDetail instances, which define instances of Jobs.
			*TriggerBuilder - used to define/build Trigger instances.
		 */
	}

	// 简单任务 每s/m/h执行多少次等
	public static void test2() throws Exception {
		// Scheduler 调度器
		SchedulerFactory schedFact = new org.quartz.impl.StdSchedulerFactory();
		Scheduler sched = schedFact.getScheduler();

		// job
		JobDetail job = JobBuilder.newJob(MyJob1.class)
			.withIdentity("job1", "group1")//名称和组
			.usingJobData("JobName", "Hello World!")//设置参数，传递参数,在工作类中可以获取到
			.build();

		// trigger 每3秒执行一次 供执行6次
		Trigger trigger = TriggerBuilder.newTrigger()
				.withIdentity("trigger_1", "group_1")
				.startNow()// 立即执行
				.withSchedule(SimpleScheduleBuilder.simpleSchedule()
				.withIntervalInSeconds(3)
				//.withIntervalInMinutes(1)
				//.withRepeatCount(5)// 总次数是5次
				.repeatForever()// 无限重复
		    ).build();

		sched.scheduleJob(job, trigger);
		sched.start();
	}

	@Test
	// 表达式
	    public
	    void test1() throws Exception {
		// Scheduler 调度器
		SchedulerFactory schedFact = new org.quartz.impl.StdSchedulerFactory();
		Scheduler sched = schedFact.getScheduler();
		sched.start();

		// define the job and tie it to our HelloJob class
		JobDetail job = JobBuilder.newJob(MyJob1.class).withIdentity("myJob", "group1").build();

		// Trigger the job to run now, and then every 40 seconds
		Trigger trigger = TriggerBuilder.newTrigger().withIdentity("simpleTrigger", "triggerGroup").withSchedule(CronScheduleBuilder.cronSchedule("0 * * * * ?")).startNow().build();

		// Tell quartz to schedule the job using our trigger
		sched.scheduleJob(job, trigger);
	}
}
