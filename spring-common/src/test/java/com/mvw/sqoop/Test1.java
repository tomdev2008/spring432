//package com.mvw.sqoop;
//
//import org.apache.sqoop.client.SqoopClient;
//import org.apache.sqoop.model.MDriverConfig;
//import org.apache.sqoop.model.MFromConfig;
//import org.apache.sqoop.model.MJob;
//import org.apache.sqoop.model.MLink;
//import org.apache.sqoop.model.MLinkConfig;
//import org.apache.sqoop.model.MSubmission;
//import org.apache.sqoop.model.MToConfig;
//import org.apache.sqoop.submission.counter.Counter;
//import org.apache.sqoop.submission.counter.CounterGroup;
//import org.apache.sqoop.submission.counter.Counters;
//import org.apache.sqoop.validation.Status;
//
//public class Test1 {
//	public static void main(String[] args) {
//		sqoopTransfer();
//	}
//
//	public static void sqoopTransfer() {
//		// 初始化
//		String url = "http://hadoop:12000/sqoop/";
//		SqoopClient client = new SqoopClient(url);
//
//		// 创建一个源链接 JDBC
//		long fromConnectorId = 2;
//		MLink fromLink = client.createLink(fromConnectorId);
//		fromLink.setName("JDBC connector");
//		fromLink.setCreationUser("admln");
//		MLinkConfig fromLinkConfig = fromLink.getConnectorLinkConfig();
//		fromLinkConfig.getStringInput("linkConfig.connectionString").setValue("jdbc:mysql://hadoop:3306/hive");
//		fromLinkConfig.getStringInput("linkConfig.jdbcDriver").setValue("com.mysql.jdbc.Driver");
//		fromLinkConfig.getStringInput("linkConfig.username").setValue("hive");
//		fromLinkConfig.getStringInput("linkConfig.password").setValue("hive");
//		Status fromStatus = client.saveLink(fromLink);
//		if (fromStatus.canProceed()) {
//			System.out.println("创建JDBC Link成功，ID为: " + fromLink.getPersistenceId());
//		} else {
//			System.out.println("创建JDBC Link失败");
//		}
//		
//		// 创建一个目的地链接HDFS
//		String toConnectorId = "1";
//		MLink toLink = client.createLink(toConnectorId);
//		toLink.setName("HDFS connector");
//		toLink.setCreationUser("admln");
//		MLinkConfig toLinkConfig = toLink.getConnectorLinkConfig();
//		toLinkConfig.getStringInput("linkConfig.uri").setValue("hdfs://hadoop:8020/");
//		Status toStatus = client.saveLink(toLink);
//		if (toStatus.canProceed()) {
//			System.out.println("创建HDFS Link成功，ID为: " + toLink.getPersistenceId());
//		} else {
//			System.out.println("创建HDFS Link失败");
//		}
//
//		// 创建一个任务
//		long fromLinkId = fromLink.getPersistenceId();
//		long toLinkId = toLink.getPersistenceId();
//		MJob job = client.createJob(fromLinkId, toLinkId);
//		job.setName("MySQL to HDFS job");
//		job.setCreationUser("admln");
//		// 设置源链接任务配置信息
//		MFromConfig fromJobConfig = job.getFromJobConfig();
//		fromJobConfig.getStringInput("fromJobConfig.schemaName").setValue("sqoop");
//		fromJobConfig.getStringInput("fromJobConfig.tableName").setValue("sqoop");
//		fromJobConfig.getStringInput("fromJobConfig.partitionColumn").setValue("id");
//		MToConfig toJobConfig = job.getToJobConfig();
//		toJobConfig.getStringInput("toJobConfig.outputDirectory").setValue("/usr/tmp");
//		MDriverConfig driverConfig = job.getDriverConfig();
//		driverConfig.getStringInput("throttlingConfig.numExtractors").setValue("3");
//
//		Status status = client.saveJob(job);
//		if (status.canProceed()) {
//			System.out.println("JOB创建成功，ID为: " + job.getPersistenceId());
//		} else {
//			System.out.println("JOB创建失败。");
//		}
//
//		// 启动任务
//		long jobId = job.getPersistenceId();
//		MSubmission submission = client.startJob(jobId);
//		System.out.println("JOB提交状态为 : " + submission.getStatus());
//		while (submission.getStatus().isRunning() && submission.getProgress() != -1) {
//			System.out.println("进度 : " + String.format("%.2f %%", submission.getProgress() * 100));
//			// 三秒报告一次进度
//			try {
//				Thread.sleep(3000);
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			}
//		}
//		System.out.println("JOB执行结束... ...");
//		System.out.println("Hadoop任务ID为 :" + submission);
//		Counters counters = submission.getCounters();
//		if (counters != null) {
//			System.out.println("计数器:");
//			for (CounterGroup group : counters) {
//				System.out.print("\t");
//				System.out.println(group.getName());
//				for (Counter counter : group) {
//					System.out.print("\t\t");
//					System.out.print(counter.getName());
//					System.out.print(": ");
//					System.out.println(counter.getValue());
//				}
//			}
//		}
//		if (submission.getError()!=null) {
//			System.out.println("JOB执行异常，异常信息为 : " + submission.getError());
//		}
//		System.out.println("MySQL通过sqoop传输数据到HDFS统计执行完毕");
//	}
//}