package com.kanezheng.app.jobmanagement.jobs;

import com.kanezheng.app.jobmanagement.dao.schedule.EmailJobStatus;
import com.kanezheng.app.jobmanagement.dao.schedule.EmailNotifyJobEntity;
import com.kanezheng.app.jobmanagement.repository.schedule.EmailNotifyJobRepository;
import com.kanezheng.app.jobmanagement.service.schedule.ScheduleServiceImpl;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import java.time.OffsetDateTime;

import static com.kanezheng.app.jobmanagement.dao.schedule.EmailJobStatus.STANDBY;

@Component
public class EmailNotifyJob implements Job{

    Logger logger = LoggerFactory.getLogger(EmailNotifyJob.class);

    @Autowired
    EmailNotifyJobRepository emailNotifyJobRepository;

    @Override
    public void execute(JobExecutionContext context) {
//        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);

        Scheduler scheduler = context.getScheduler();
        JobDataMap dataMap = context.getJobDetail().getJobDataMap();

        String userName = dataMap.getString("userName");
        Long dashboardId = dataMap.getLong("dashboardId");
        String widgetId = dataMap.getString("widgetId");
        Long scheduleId = dataMap.getLong("scheduleId");
        EmailJobStatus emailJobStatus = STANDBY;

        EmailNotifyJobEntity emailNotifyJobEntity = EmailNotifyJobEntity.builder()
                                                .userName(userName)
                                                .dashboardId(dashboardId)
                                                .widgetId(widgetId)
                                                .scheduleId(scheduleId)
                                                .status(emailJobStatus)
                                                .build();

        logger.info("####Before save to database####, time:{}", OffsetDateTime.now());

//        emailNotifyJobRepository.save(emailNotifyJobEntity);

        try {
            String jobDescription = context.getJobDetail().getDescription();
            logger.info("Trigger a new job: {} in scheduler {}", jobDescription, scheduler.getSchedulerName());
        }catch (Exception e){

        }
    }
}
