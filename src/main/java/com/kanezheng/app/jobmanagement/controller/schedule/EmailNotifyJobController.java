package com.kanezheng.app.jobmanagement.controller.schedule;

import com.kanezheng.app.jobmanagement.dao.schedule.EmailNotifyJobEntity;
import com.kanezheng.app.jobmanagement.dao.schedule.EmailNotifyJobRest;
import com.kanezheng.app.jobmanagement.dao.schedule.Schedule;
import com.kanezheng.app.jobmanagement.service.schedule.EmailNotifyJobService;
import com.kanezheng.app.jobmanagement.service.schedule.ScheduleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class EmailNotifyJobController {
    Logger logger = LoggerFactory.getLogger(ScheduleController.class);

    @Autowired
    private EmailNotifyJobService emailNotifyJobService;

    @Autowired
    private ScheduleService scheduleService;

    @GetMapping("/emailnotifyjob")
    public EmailNotifyJobRest getEmailNotifyJob() throws Exception {

        //1. Get next job from the jobs queue
        //2. Get email recipients from schedule setting
        //3. Compose EmailNotifyJobRest to response to Node Server
        EmailNotifyJobEntity emailNotifyJobEntity = emailNotifyJobService.getEmailNotifyJob();
        Schedule schedule = scheduleService.getScheduleByWidgetId(emailNotifyJobEntity.getWidgetId());

        EmailNotifyJobRest emailNotifyJobRest = EmailNotifyJobRest.builder()
                                                .userName(emailNotifyJobEntity.getUserName())
                                                .dashboardId(emailNotifyJobEntity.getDashboardId())
                                                .widgetId(emailNotifyJobEntity.getWidgetId())
                                                .scheduleId(emailNotifyJobEntity.getScheduleId())
                                                .status(emailNotifyJobEntity.getStatus())
                                                .emailRecipients(schedule.getEmailRecipients())
                                                .build();
        return emailNotifyJobRest;
    }

    @PostMapping("/emailnotifyresult")
    public void updateEmailNotifyResult(@Valid @RequestBody EmailNotifyJobRest emailNotifyJobRest) throws Exception {
        emailNotifyJobService.updateEmailNofifyJobStatus(emailNotifyJobRest);
        logger.info("updateEmailNotifyResult: {}", emailNotifyJobRest);
    }
}
