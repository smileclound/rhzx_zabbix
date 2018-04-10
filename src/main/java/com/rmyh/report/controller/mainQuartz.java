package com.rmyh.report.controller;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import static org.quartz.JobBuilder.*;
import static org.quartz.TriggerBuilder.*;
import static org.quartz.SimpleScheduleBuilder.*;

import com.rmyh.report.service.hbase.DataInsert;

public class mainQuartz {

	// org.quartz.scheduler.instanceName = MyScheduler;
	// org.quartz.threadPool.threadCount = 3;
	// org.quartz.jobStore.class = org.quartz.simpl.RAMJobStore;
	public static final int quarztgap = 120; //300 seconds  

	public static void main(String[] args) {
		try {
			// Grab the Scheduler instance from the Factory
			Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();

			// and start it off
			scheduler.start();
			// define the job and tie it to our HelloJob class
			JobDetail job = newJob(DataInsert.class).withIdentity("job1", "group1").build();

			// Trigger the job to run now, and then repeat every 40 seconds
			Trigger trigger = newTrigger().withIdentity("trigger1", "group1").startNow()
					.withSchedule(simpleSchedule().withIntervalInSeconds(quarztgap).repeatForever()).build();

			// Tell quartz to schedule the job using our trigger
			scheduler.scheduleJob(job, trigger);
//			scheduler.shutdown();

		} catch (SchedulerException se) {
			se.printStackTrace();
		}
	}

}
