package com.kanezheng.app.jobmanagement.jobs;

import com.kanezheng.app.jobmanagement.repository.schedule.EmailNotifyJobRepository;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;

public class EmailNotifyJob implements Job{

    @Autowired
    EmailNotifyJobRepository emailNotifyJobRepository;

    @Override
    public void execute(JobExecutionContext context) {

        Scheduler scheduler = context.getScheduler();

        JobDataMap dataMap = context.getJobDetail().getJobDataMap();


        String jobDescription = context.getJobDetail().getDescription();
        try {
            System.out.println("Hello this is : "+ jobDescription + " running in scheduler: "+ scheduler.getSchedulerName());
            System.out.println("dataMap: "+dataMap.toString());
            System.out.println("key: "+context.getJobDetail().getKey());
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }
}
