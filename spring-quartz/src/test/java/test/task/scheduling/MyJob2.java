package test.task.scheduling;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class MyJob2 implements Job {

	public void execute(JobExecutionContext context) throws JobExecutionException {
		System.out.println("job2");
		new MyQuartzTest().showTime("yyyy-MM-dd HH:mm:ss");
	}
}