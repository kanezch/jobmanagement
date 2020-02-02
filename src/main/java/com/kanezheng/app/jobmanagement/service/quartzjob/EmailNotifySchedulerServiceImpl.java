package com.kanezheng.app.jobmanagement.service.quartzjob;

import com.kanezheng.app.jobmanagement.dao.schedule.Schedule;
import com.kanezheng.app.jobmanagement.jobs.HelloWorldJob;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.Trigger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

@Service
public class EmailNotifySchedulerServiceImpl implements EmailNotifySchedulerService{


    Logger logger = LoggerFactory.getLogger(EmailNotifySchedulerServiceImpl.class);

    @Autowired
    private final Scheduler scheduler;

    public EmailNotifySchedulerServiceImpl(Scheduler scheduler) {
        this.scheduler = scheduler;
    }

    @Override
    public int createEmailNotifyJob(Schedule schedule) throws Exception {

        String jobNumber = "1";

        String jobName = "job"+jobNumber;
        String triggerName = "trigger"+jobNumber;

        JobDataMap jobDataMap = new JobDataMap();


        JobDetail job = newJob(HelloWorldJob.class)
                .withIdentity(jobName, "group1")
                .usingJobData("jobName", jobName)
                .build();

        Trigger trigger = newTrigger()
                .withIdentity(triggerName, "group1")
                .startNow()
                .withSchedule(simpleSchedule()
                        .withIntervalInSeconds(1)
                        .repeatForever())
                .build();


        scheduler.scheduleJob(job, trigger);

        logger.info("scheduler.scheduleJob");

        return 0;
    }

    @Override
    public int updateEmailNotifyJob(Schedule schedule) throws Exception {
        return 0;
    }

    @Override
    public void deleteEmailNotifyJob(Schedule schedule) throws Exception {

    }
}
