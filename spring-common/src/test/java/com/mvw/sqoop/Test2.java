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
//public class Test2 {
//
//	public static void main(String[] args) {
//		sqoopTransfer();
//	}
//
//	private static void sqoopTransfer() {
//
//		// init
//		String url = "http://localhost:12000/sqoop/";
//		SqoopClient client = new SqoopClient(url);
//
//		// create a placeholder for link
//		long connectorId = 1;
//		MLink link = client.createLink(connectorId);
//		link.setName("Vampire");
//		link.setCreationUser("Buffy");
//		MLinkConfig linkConfig = link.getConnectorLinkConfig();
//		// fill in the link config values
//		linkConfig.getStringInput("linkConfig.connectionString").setValue("jdbc:mysql://localhost/my");
//		linkConfig.getStringInput("linkConfig.jdbcDriver").setValue("com.mysql.jdbc.Driver");
//		linkConfig.getStringInput("linkConfig.username").setValue("root");
//		linkConfig.getStringInput("linkConfig.password").setValue("root");
//		// save the link object that was filled
//		Status status = client.saveLink(link);
//		if (status.canProceed()) {
//			System.out.println("Created Link with Link Id : " + link.getPersistenceId());
//		} else {
//			System.out.println("Something went wrong creating the link");
//		}
//
//		// Creating dummy job object
//		long fromLinkId = 1;// for jdbc connector
//		long toLinkId = 2; // for HDFS connector
//		MJob job = client.createJob(fromLinkId, toLinkId);
//		job.setName("Vampire");
//		job.setCreationUser("Buffy");
//		// set the "FROM" link job config values
//		MFromConfig fromJobConfig = job.getFromJobConfig();
//		fromJobConfig.getStringInput("fromJobConfig.schemaName").setValue("sqoop");
//		fromJobConfig.getStringInput("fromJobConfig.tableName").setValue("sqoop");
//		fromJobConfig.getStringInput("fromJobConfig.partitionColumn").setValue("id");
//		// set the "TO" link job config values
//		MToConfig toJobConfig = job.getToJobConfig();
//		toJobConfig.getStringInput("toJobConfig.outputDirectory").setValue("/usr/tmp");
//		// set the driver config values
//		MDriverConfig driverConfig = job.getDriverConfig();
//		driverConfig.getStringInput("throttlingConfig.numExtractors").setValue("3");
//
//		status = client.saveJob(job);
//		if (status.canProceed()) {
//			System.out.println("Created Job with Job Id: " + job.getPersistenceId());
//		} else {
//			System.out.println("Something went wrong creating the job");
//		}
//
//		// Job start
//		long jobId = 1;
//		MSubmission submission = client.startJob(jobId);
//		System.out.println("Job Submission Status : " + submission.getStatus());
//		if (submission.getStatus().isRunning() && submission.getProgress() != -1) {
//			System.out.println("Progress : " + String.format("%.2f %%", submission.getProgress() * 100));
//		}
//		System.out.println("Hadoop job id :" + submission.getExternalJobId());
//		System.out.println("Job link : " + submission.getExternalLink());
//		Counters counters = submission.getCounters();
//		if (counters != null) {
//			System.out.println("Counters:");
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
//			System.out.println("Exception info : " + submission.getError());
//		}
//
//		// Check job status for a running job
//		submission = client.getJobStatus(jobId);
//		if (submission.getStatus().isRunning() && submission.getProgress() != -1) {
//			System.out.println("Progress : " + String.format("%.2f %%", submission.getProgress() * 100));
//		}
//
//		// Stop a running job
//		//submission.stopJob(jobId);
//	}
//
//}
