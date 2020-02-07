package com.kanezheng.app.jobmanagement.service.quartzjob;

import com.kanezheng.app.jobmanagement.dao.schedule.CustomRepeatType;
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
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.TimeZone;

import static com.kanezheng.app.jobmanagement.dao.schedule.CustomRepeatType.DAYS;
import static com.kanezheng.app.jobmanagement.dao.schedule.ScheduleRepeatType.CUSTOM;
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

        Trigger trigger = null;
        Date triggerStartTime = Date.from(schedule.getInitialDeliverTime().toInstant());
        if (schedule.getScheduleRepeatType() == ScheduleRepeatType.ONE_OFF){

            trigger = newTrigger()
            .withIdentity(triggerName, EMAIL_NOTIFY_JOBS_GROUP)
            .startAt(triggerStartTime)
            .forJob(jobName, EMAIL_NOTIFY_JOBS_GROUP)
            .build();

            logger.info("[Schedule CRUD] This one time job will be triggered at {}", triggerStartTime);

        }else if ((schedule.getScheduleRepeatType() == CUSTOM) &&
                  (schedule.getCustomRepeatType()== DAYS)){

            trigger = newTrigger()
                    .withIdentity(triggerName, EMAIL_NOTIFY_JOBS_GROUP)
                    .startAt(triggerStartTime)
                    .withSchedule(simpleSchedule().withIntervalInHours(schedule.getCustomRepeatValue() * 24))
                    .build();

            logger.info("[Schedule CRUD] New job will be triggered at {}, and repeat every {} days.",
                    triggerStartTime,schedule.getCustomRepeatValue());

        }else{

            String cronExpression = ScheduleUtil.composeCronExpression(schedule);
            ZoneId zoneId = ZoneId.ofOffset("UTC",schedule.getInitialDeliverTime().getOffset());
            TimeZone timeZone = TimeZone.getTimeZone(zoneId);

            trigger = newTrigger()
                    .withIdentity(triggerName, EMAIL_NOTIFY_JOBS_GROUP)
                    .startNow()
                    .withSchedule(cronSchedule(cronExpression).inTimeZone(timeZone))
                    .forJob(jobName, EMAIL_NOTIFY_JOBS_GROUP)
                    .build();

            String humanReadableTxt = ScheduleUtil.translateCronExpressToHumanReadableTxt(cronExpression);
            logger.info("[Schedule CRUD] This cron job will be triggered : {} in timezone {}", humanReadableTxt, zoneId);
        }

        scheduler.scheduleJob(job, trigger);
        logger.info("[Schedule CRUD] Schedule a new job:{}", jobName);
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
        logger.info("[Schedule CRUD] Delete job {} success", jobName);
    }
}
