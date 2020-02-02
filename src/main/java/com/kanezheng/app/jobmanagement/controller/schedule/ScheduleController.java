package com.kanezheng.app.jobmanagement.controller.schedule;

import com.kanezheng.app.jobmanagement.dao.schedule.Schedule;
import com.kanezheng.app.jobmanagement.service.quartzjob.EmailNotifySchedulerService;
import com.kanezheng.app.jobmanagement.service.quartzjob.EmailNotifySchedulerServiceImpl;
import com.kanezheng.app.jobmanagement.service.schedule.ScheduleService;
import org.quartz.Scheduler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/dashboard/{dashboardId}/widget/{widgetId}")
public class ScheduleController {
    Logger logger = LoggerFactory.getLogger(ScheduleController.class);

    @Autowired
    private ScheduleService scheduleService;

    @Autowired
    private EmailNotifySchedulerService emailNotifySchedulerService;

    @PostMapping("/schedule")
    public Schedule createSchedule(@PathVariable Long dashboardId,
                                   @PathVariable String widgetId,
                                   @Valid @RequestBody Schedule schedule) throws Exception{

        Schedule scheduleResp = scheduleService.createSchedule(schedule);

        int result = emailNotifySchedulerService.createEmailNotifyJob(schedule);

        logger.info("Craete a job result:", result);

        return scheduleResp;
    }

    @GetMapping("/schedule")
    public Schedule getSchedule(@PathVariable Long dashboardId, @PathVariable String widgetId) throws Exception{

        return scheduleService.getScheduleByWidgetId(widgetId);
    }

    @PutMapping("/schedule/{scheduleId}")
    public Schedule updateSchedule(@PathVariable Long dashboardId,
                                   @PathVariable String widgetId,
                                   @PathVariable Long scheduleId,
                                   @Valid @RequestBody Schedule scheduleRequest) throws Exception{
        return scheduleService.updateSchedule(scheduleId, scheduleRequest);
    }

    @DeleteMapping("/schedule/{scheduleId}")
    public void deleteSchedule(@PathVariable Long dashboardId,
                                            @PathVariable String widgetId,
                                            @PathVariable Long scheduleId) throws Exception{
        scheduleService.deleteSchedule(scheduleId);
    }

    @DeleteMapping("/schedule")
    public void deleteSchedule(@PathVariable Long dashboardId,
                               @PathVariable String widgetId) throws Exception{
        scheduleService.deleteScheduleByWidgetId(widgetId);
    }
}
