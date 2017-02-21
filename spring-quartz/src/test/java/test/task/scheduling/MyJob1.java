package test.task.scheduling;

import java.util.Date;

import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobKey;

public class MyJob1 implements Job {
	public void execute(JobExecutionContext context) throws JobExecutionException {
		System.out.println("job1");
		JobKey key = context.getJobDetail().getKey();
		JobDataMap dataMap = context.getJobDetail().getJobDataMap();
		String JobName = dataMap.getString("JobName");
		System.out.println("Instance " + key + " of DumbJob says: " + JobName);
		System.out.println(new Date());
	}
}