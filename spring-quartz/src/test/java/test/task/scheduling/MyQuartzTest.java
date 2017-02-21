package test.task.scheduling;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerFactory;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

//适合繁杂系统
public class MyQuartzTest {

	
	public static void main(String[] args) {
		
		test2();
	}
	
	
	//每个任务JobDetail可以绑定多个Trigger，但一个Trigger只能绑定一个任务
	public static void test2() {
		SchedulerFactory schedulerfactory = new StdSchedulerFactory();
		
		Scheduler scheduler = null;
		try {
			// sch
			scheduler = schedulerfactory.getScheduler();

			// jobDetail
			JobDetail job = JobBuilder
							.newJob(MyJob1.class)
							.withIdentity("job", "jgroup")//group 其实就是一个分类，命令空间
							.storeDurably(true)
							.build();
			scheduler.addJob(job, false, true);
			
			//trigger
			Trigger trigger1 = TriggerBuilder.newTrigger()
							  .withIdentity("simpleTrigger", "jgroup1")
							  .withSchedule(CronScheduleBuilder.cronSchedule("0/5 * * * * ? *"))
							  .forJob("job","jgroup")
							  .startNow()
							  .build();
			
			Trigger trigger2 = TriggerBuilder.newTrigger()
					  .withIdentity("simpleTrigger", "jgroup2")
					  .withSchedule(CronScheduleBuilder.cronSchedule("0/6 * * * * ? *"))
					  .forJob(job)
					  .startNow()
					  .build();
			
			
			// 把作业和触发器注册到任务调度中
			scheduler.scheduleJob(trigger1);
			scheduler.scheduleJob(trigger2);

			// 启动调度
			scheduler.start();
			//scheduler.shutdown();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void test1() {
		SchedulerFactory schedulerfactory = new StdSchedulerFactory();
		
		Scheduler scheduler = null;
		try {
			// sch
			scheduler = schedulerfactory.getScheduler();

			// jobDetail
			JobDetail job = JobBuilder
							.newJob(MyJob1.class)
							.withIdentity("job", "jgroup")
							.build();
			
			//trigger
			Trigger trigger = TriggerBuilder.newTrigger()
							  .withIdentity("simpleTrigger", "jgroup")
							  .withSchedule(CronScheduleBuilder.cronSchedule("0/5 * * * * ? *"))
							  .startNow()
							  .build();
		
			
			// 把作业和触发器注册到任务调度中
			scheduler.scheduleJob(job,trigger);

			// 启动调度
			scheduler.start();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void showTime(String str) {
		System.out.println(new SimpleDateFormat(str).format(new Date()));
	}

	/**
	 * 一、TriggerUtils 方便和实用方法,以简化施工和配置触发器和日期 
	 * 
	 * //天
	 * makeDailyTrigger(int hour, int minute) 
	 * makeDailyTrigger(String trigName, int hour, int minute)
	 * 每天在给定的时间执行
	 *  hour : 0-23 
	 *  minute : 0-59 
	 *  
	 *  
	 *  
	 *  //小时 
	 *  makeHourlyTrigger() 每小时循环执行 
	 *  makeHourlyTrigger(int intervalInHours) 每N个小时执行一次
	 *  makeHourlyTrigger(int intervalInHours, int repeatCount) 每N个小时执行一次 执行指定的次数 
	 *  makeHourlyTrigger(String trigName) //有名称
	 *  makeHourlyTrigger(String trigName, int intervalInHours, int repeatCount)
	 * //在repeatCount时触发,循环执行 间隔是repeatInterval毫秒 
	 * 
	 * makeImmediateTrigger(int
	 * repeatCount, long repeatInterval) makeImmediateTrigger(String trigName,
	 * int repeatCount, long repeatInterval) //分钟 makeMinutelyTrigger()
	 * makeMinutelyTrigger(int intervalInMinutes) makeMinutelyTrigger(int
	 * intervalInMinutes, int repeatCount) makeMinutelyTrigger(String trigName)
	 * makeMinutelyTrigger(String trigName, int intervalInMinutes, int
	 * repeatCount) //每个月在给定的时间触发 makeMonthlyTrigger(int dayOfMonth, int hour,
	 * int minute) dayOfMonth - (1-31, or -1) the day of week upon which to fire
	 * hour - the hour (0-23) upon which to fire minute - the minute (0-59) upon
	 * which to fire 说明：如果给定的天数不在指定的月中 将不会触发 makeMonthlyTrigger(String trigName,
	 * int dayOfMonth, int hour, int minute) //每一秒执行一次 makeSecondlyTrigger()
	 * makeSecondlyTrigger(int intervalInSeconds) 间隔指定的秒 makeSecondlyTrigger(int
	 * intervalInSeconds, int repeatCount) 间隔指定的秒 执行指定的次数
	 * makeSecondlyTrigger(String trigName) makeSecondlyTrigger(String trigName,
	 * int intervalInSeconds, int repeatCount) //每周执行 makeWeeklyTrigger(int
	 * dayOfWeek, int hour, int minute) dayOfWeek - (1-7) the day of week upon
	 * which to fire hour - the hour (0-23) upon which to fire minute - the
	 * minute (0-59) upon which to fire makeWeeklyTrigger(String trigName, int
	 * dayOfWeek, int hour, int minute) //适合简单任务的 : 在指定的时间开始 以多大的间隔执行多少次
	 * 在指定的时间结束 二、SimpleTrigger(String name, //名称 String group, //组 Date
	 * startTime, //开始时间 Date endTime, //结束时间 int repeatCount, //执行次数 long
	 * repeatInterval)//循环次数 同时设置了 end-time 与 repeat count，则优先考虑 end-time
	 * //还有一下几个构造 SimpleTrigger() SimpleTrigger(String name)
	 * SimpleTrigger(String name, Date startTime) SimpleTrigger(String name,
	 * Date startTime, Date endTime, int repeatCount, long repeatInterval)
	 * SimpleTrigger(String name, int repeatCount, long repeatInterval)
	 * SimpleTrigger(String name, String group) SimpleTrigger(String name,
	 * String group, Date startTime) SimpleTrigger(String name, String group,
	 * Date startTime, Date endTime, int repeatCount, long repeatInterval)
	 * SimpleTrigger(String name, String group, int repeatCount, long
	 * repeatInterval) SimpleTrigger(String name, String group, String jobName,
	 * String jobGroup, Date startTime, Date endTime, int repeatCount, long
	 * repeatInterval) // 支持表达式 三、CronTrigger 同样需要指定 start-time 和 end-time，其核心在于
	 * Cron 表达式，由七个字段组成： Seconds 秒 Minutes 分 Hours 时 Day-of-Month 天 Month 月
	 * Day-of-Week 周 Year (Optional field) 年[可选字段] 即：秒 分 时 天 月 周 年 各参数的说明：
	 * Seconds 和 Minutes 取值为 0 到 59 Hours 取值为 0 到 23 Day-of-Month 取值为 0-31 Month
	 * 取值为 0-11 Days-of-Week 取值为 1-7 周日-周六 每个字段可以取单个值，多个值，或一个范围，例如 Day-of-Week
	 * 可取值为“MON，TUE，SAT”,“MON-FRI”或者“TUE-THU，SUN”。 几个例子： 创建一个每三小时执行的
	 * CronTrigger，且从每小时的整点开始执行： 0 0 0/3 * * ? 创建一个每十分钟执行的
	 * CronTrigger，且从每小时的第三分钟开始执行： 0 3/10 * * * ? 创建一个每周一，周二，周三，周六的晚上 20:00 到
	 * 23:00，每半小时执行一次的 CronTrigger： 0 0/30 20-23 ? * MON-WED,SAT 创建一个每月最后一个周四，中午
	 * 11:30-14:30，每小时执行一次的 trigger： 0 30 11-14/1 ? * 5L 通配符 * 表示该字段可接受任何可能取值。例如
	 * Month 字段赋值 * 表示每个月 / 表示开始时刻与间隔时段。例如 Minutes 字段赋值 2/10 表示在一个小时内每 10
	 * 分钟执行一次，从第 2 分钟开始。 ? 仅适用于 Day-of-Month 和 Day-of-Week。? 表示对该字段不指定特定值。
	 * 适用于需要对这两个字段中的其中一个指定值，而对另一个不指定值的情况。一般情况下，这两个字段只需对一个赋值。 L 仅适用于 Day-of-Month
	 * 和 Day-of-Week。L 用于 Day-of-Month 表示该月最后一天。 L 单独用于 Day-of-Week
	 * 表示周六，否则表示一个月最后一个星期几，例如 5L 或者 THUL 表示该月最后一个星期四。 W 仅适用于
	 * Day-of-Month，表示离指定日期最近的一个工作日，例如 Day-of-Month 赋值为 10W 表示该月离 10 号最近的一个工作日。
	 * # 仅适用于 Day-of-Week，表示该月第 XXX 个星期几。例如 Day-of-Week 赋值为 5#2 或者
	 * THU#2，表示该月第二个星期四。 使用例子： CronTrigger cronTrigger = new
	 * CronTrigger("myTrigger", "myGroup");
	 * cronTrigger.setCronExpression("0 0/30 20-13 ? * MON-WED,SAT"); 表达式：
	 * 
	 * 三类监听器：分别监听下面三个对象的异常 JobListener，TriggerListener 以及 SchedulerListener
	 * 实现接口，注册 sched.addJobListener(new MyListener());
	 * jobDetail.addJobListener("My Listener"); // listener 的名字
	 * sched.addGlobalJobListener(new MyListener()); //全局注册
	 */
}
