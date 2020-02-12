package com.kanezheng.app.jobmanagement.jobs;

import com.kanezheng.app.jobmanagement.dao.schedule.EmailJobStatus;
import com.kanezheng.app.jobmanagement.dao.schedule.EmailNotifyJobEntity;
import com.kanezheng.app.jobmanagement.repository.schedule.EmailNotifyJobRepository;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.kanezheng.app.jobmanagement.dao.schedule.EmailJobStatus.STANDBY;

@Component
public class EmailNotifyJob implements Job{

    Logger logger = LoggerFactory.getLogger(EmailNotifyJob.class);

    @Autowired
    EmailNotifyJobRepository emailNotifyJobRepository;

    @Override
    public void execute(JobExecutionContext context) {

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

        emailNotifyJobRepository.save(emailNotifyJobEntity);
        String jobDescription = context.getJobDetail().getDescription();
        logger.info("[Scheduler Service] Trigger a new job: {}", jobDescription);
    }
}
