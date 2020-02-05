package com.kanezheng.app.jobmanagement.service.quartzjob;

import com.kanezheng.app.jobmanagement.dao.schedule.Schedule;
import com.kanezheng.app.jobmanagement.jobs.EmailNotifyJob;
import com.kanezheng.app.jobmanagement.repository.schedule.EmailNotifyJobRepository;
import com.kanezheng.app.jobmanagement.util.ScheduleUtil;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.Trigger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.JobKey.jobKey;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.CronScheduleBuilder.cronSchedule;
import static org.quartz.TriggerBuilder.newTrigger;
import static org.quartz.TriggerKey.triggerKey;

@Service
public class EmailNotifySchedulerServiceImpl implements EmailNotifySchedulerService{


    Logger logger = LoggerFactory.getLogger(EmailNotifySchedulerServiceImpl.class);
    static final String EMAIL_NOTIFY_JOBS_GROUP = "EmailNotifyJobs";

    @Autowired
    private Scheduler scheduler;

    @Autowired
    private EmailNotifyJobRepository emailNotifyJobRepository;

    @Override
    public int createEmailNotifyJob(String userName, Long dashboardId, Schedule schedule) throws Exception {


        String namePostFix = ScheduleUtil.composeEmailNotifyJobName(userName,
                                                                    dashboardId,
                                                                    schedule.getWidgetId(),
                                                                    schedule.getId());
        String jobName = "EmailJob-" + namePostFix;
        String triggerName = "EmailTrigger-" + namePostFix;

        JobDetail job = newJob(EmailNotifyJob.class)
                        .withIdentity(jobName, EMAIL_NOTIFY_JOBS_GROUP)
                        .usingJobData("userName", userName)
                        .usingJobData("dashboardId", dashboardId)
                        .usingJobData("widgetId", schedule.getWidgetId())
                        .usingJobData("scheduleId", schedule.getId())
                        .withDescription(jobName)
//                        .usingJobData("repository", String.valueOf(emailNotifyJobRepository))
                        .build();


        String cronExpression = ScheduleUtil.composeCronExpression(schedule);
        logger.info("composeCronExpression: {}", cronExpression);

        Trigger trigger = newTrigger()
                .withIdentity(triggerName, EMAIL_NOTIFY_JOBS_GROUP)
                .startNow()
                .withSchedule(cronSchedule(cronExpression))
                .forJob(jobName, EMAIL_NOTIFY_JOBS_GROUP)
                .build();

        scheduler.scheduleJob(job, trigger);

        logger.info("scheduler.scheduleJob");

        return 0;
    }

    @Override
    public int updateEmailNotifyJob(Schedule schedule) throws Exception {

        // Define a new Trigger
        Trigger trigger = newTrigger()
                .withIdentity("newTrigger", "group1")
                .startNow()
                .withSchedule(simpleSchedule().withIntervalInSeconds(5).repeatForever())
                .build();

        // tell the scheduler to remove the old trigger with the given key, and put the new one in its place
        scheduler.rescheduleJob(triggerKey("trigger1", "group1"), trigger);

        logger.info("Reschedule job, replace trigger1 with newTrigger");
        return 0;
    }

/*    @Override
    public void deleteEmailNotifyJob(Schedule schedule) throws Exception {
        scheduler.deleteJob(jobKey("job1", "group1"));
        logger.info("Delete job1!");
    }*/

    @Override
    public void deleteEmailNotifyJob() throws Exception {
        scheduler.deleteJob(jobKey("job1", "group1"));
    }
}
