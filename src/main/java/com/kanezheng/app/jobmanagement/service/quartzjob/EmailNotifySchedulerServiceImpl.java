package com.kanezheng.app.jobmanagement.service.quartzjob;

import com.kanezheng.app.jobmanagement.dao.schedule.Schedule;
import com.kanezheng.app.jobmanagement.dao.schedule.ScheduleRepeatType;
import com.kanezheng.app.jobmanagement.dao.schedule.ScheduleRepeatType.*;
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

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.TimeZone;

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


/*        //TEST ONE-OFF
        OffsetDateTime odt2 = OffsetDateTime.of(LocalDateTime.of(2020, 02, 05, 15, 15),
                ZoneOffset.of("+11:00"));
        Date d2 = Date.from(odt2.toInstant());

        logger.info("odt2:{}", odt2);
        logger.info("d2:{}", d2);*/


        Trigger trigger = null;
        if (schedule.getScheduleRepeatType() == ScheduleRepeatType.ONE_OFF){

            Date triggerStartTime = Date.from(schedule.getInitialDeliverTime().toInstant());

            logger.info("getInitialDeliverTime:{}", schedule.getInitialDeliverTime());
            logger.info("The job will be trigger at:{}", triggerStartTime);

            trigger = newTrigger()
            .withIdentity(triggerName, EMAIL_NOTIFY_JOBS_GROUP)
            .startAt(triggerStartTime)
            .forJob(jobName, EMAIL_NOTIFY_JOBS_GROUP)
            .build();
        }else{
            String cronExpression = ScheduleUtil.composeCronExpression(schedule);
            logger.info("composeCronExpression: {}", cronExpression);

             trigger = newTrigger()
                    .withIdentity(triggerName, EMAIL_NOTIFY_JOBS_GROUP)
                    .startNow()
                    .withSchedule(cronSchedule(cronExpression))
                    .forJob(jobName, EMAIL_NOTIFY_JOBS_GROUP)
                    .build();

            logger.info("scheduler.scheduleJob");
        }

        scheduler.scheduleJob(job, trigger);

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

    @Override
    public void deleteEmailNotifyJob(String userName, Long dashboardId, String widgetId, Long scheduleId) throws Exception {
        String namePostFix = ScheduleUtil.composeEmailNotifyJobName(userName, dashboardId, widgetId, scheduleId);
        String jobName = "EmailJob-" + namePostFix;

        scheduler.deleteJob(jobKey(jobName, EMAIL_NOTIFY_JOBS_GROUP));
        logger.info("Delete job {} success", jobName );
    }
}
