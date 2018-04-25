package com.rmyh.report;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import static org.quartz.JobBuilder.*;
import static org.quartz.TriggerBuilder.*;

import java.util.Date;

import static org.quartz.SimpleScheduleBuilder.*;

import com.rmyh.report.service.hbase.DataInsert;

public class quartztest {

	// org.quartz.scheduler.instanceName = MyScheduler;
	// org.quartz.threadPool.threadCount = 3;
	// org.quartz.jobStore.class = org.quartz.simpl.RAMJobStore;
	public static final int quarztgap1 = 20; //300 seconds  
	public static final int quarztgap2 = 30; //300 seconds  
	
	public static class testjob1 implements Job {
		public static void print() {
			System.out.println(new Date()+"done testjob1");
		}

		public void execute(JobExecutionContext arg0) throws JobExecutionException {
			// TODO Auto-generated method stub
			print();
		}
	}
	
	
	public static class testjob2 implements Job  {
		public static void print() {
			System.out.println(new Date()+"done testjob2");
		}

		public void execute(JobExecutionContext arg0) throws JobExecutionException {
			// TODO Auto-generated method stub
			print();
		}
	}

	public static void main(String[] args) {
		try {
			// Grab the Scheduler instance from the Factory
			Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();

			// and start it off
			scheduler.start();
			// define the job and tie it to our HelloJob class
			JobDetail job1 = newJob(testjob1.class).withIdentity("job1", "group1").build();
			JobDetail job2 = newJob(testjob2.class).withIdentity("job2", "group2").build();

			// Trigger the job to run now, and then repeat every 40 seconds
			Trigger trigger1 = newTrigger().withIdentity("trigger1", "group1").startNow()
					.withSchedule(simpleSchedule().withIntervalInSeconds(quarztgap1).repeatForever()).build();

			Trigger trigger2 = newTrigger().withIdentity("trigger2", "group2").startNow()
					.withSchedule(simpleSchedule().withIntervalInSeconds(quarztgap2).repeatForever()).build();
			
			// Tell quartz to schedule the job using our trigger
			scheduler.scheduleJob(job1, trigger1);
			scheduler.scheduleJob(job2, trigger2);
//			scheduler.shutdown();

		} catch (SchedulerException se) {
			se.printStackTrace();
		}
	}

}
