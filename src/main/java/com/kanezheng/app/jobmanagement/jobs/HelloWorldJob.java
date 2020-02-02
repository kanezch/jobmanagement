package com.kanezheng.app.jobmanagement.jobs;

import org.quartz.*;

public class HelloWorldJob implements Job {

    @Override
    public void execute(JobExecutionContext context) {

        Scheduler scheduler = context.getScheduler();


        JobDataMap dataMap = context.getJobDetail().getJobDataMap();
        String jobName = dataMap.getString("jobName");
        try {
            System.out.println("Hello this is : "+ jobName + " running in scheduler: "+ scheduler.getSchedulerName());
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }
}