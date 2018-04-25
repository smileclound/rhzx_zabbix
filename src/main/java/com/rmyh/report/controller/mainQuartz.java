package com.rmyh.report.controller;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import static org.quartz.JobBuilder.*;
import static org.quartz.TriggerBuilder.*;
import static org.quartz.SimpleScheduleBuilder.*;

import com.rmyh.report.service.hbase.DataInsert.*;
//import com.rmyh.report.service.hbase.DataInsert.XNDateInsert;

public class mainQuartz {

	// org.quartz.scheduler.instanceName = MyScheduler;
	// org.quartz.threadPool.threadCount = 3;
	// org.quartz.jobStore.class = org.quartz.simpl.RAMJobStore;
	public static final int quarztgap_XN = 120; //300 seconds  
	public static final int quarztgap_Alert = 120; //300 seconds  
	public static final int quarztgap_Trigger = 86400; //300 seconds  
	public static final int quarztgap_Item = 86400; //300 seconds  

	public static void main(String[] args) {
		try {
			// Grab the Scheduler instance from the Factory
			Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();

			// and start it off
			scheduler.start();
			// define the job and tie it to our HelloJob class
			JobDetail job1 = newJob(XNDateInsert.class).withIdentity("job1", "group1").build();
			JobDetail job2 = newJob(AlertDateInsert.class).withIdentity("job2", "group1").build();
			JobDetail job3 = newJob(TriggerDateInsert.class).withIdentity("job3", "group1").build();
			JobDetail job4 = newJob(ItemDateInsert.class).withIdentity("job4", "group1").build();

			// Trigger the job to run now, and then repeat every 40 seconds
			Trigger trigger1 = newTrigger().withIdentity("trigger1", "group1").startNow()
					.withSchedule(simpleSchedule().withIntervalInSeconds(quarztgap_XN).repeatForever()).build();
			Trigger trigger2 = newTrigger().withIdentity("trigger2", "group1").startNow()
					.withSchedule(simpleSchedule().withIntervalInSeconds(quarztgap_Alert).repeatForever()).build();
			Trigger trigger3 = newTrigger().withIdentity("trigger3", "group1").startNow()
					.withSchedule(simpleSchedule().withIntervalInSeconds(quarztgap_Trigger).repeatForever()).build();
			Trigger trigger4 = newTrigger().withIdentity("trigger4", "group1").startNow()
					.withSchedule(simpleSchedule().withIntervalInSeconds(quarztgap_Item).repeatForever()).build();

			// Tell quartz to schedule the job using our trigger
			scheduler.scheduleJob(job1, trigger1);
			scheduler.scheduleJob(job2, trigger2);
			scheduler.scheduleJob(job3, trigger3);
			scheduler.scheduleJob(job4, trigger4);
			
//			scheduler.shutdown();

		} catch (SchedulerException se) {
			se.printStackTrace();
		}
	}

}
