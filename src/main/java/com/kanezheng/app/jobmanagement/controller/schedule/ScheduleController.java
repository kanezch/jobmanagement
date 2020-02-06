package com.kanezheng.app.jobmanagement.controller.schedule;

import com.kanezheng.app.jobmanagement.dao.schedule.Schedule;
import com.kanezheng.app.jobmanagement.exception.ScheduleNotFoundException;
import com.kanezheng.app.jobmanagement.service.quartzjob.EmailNotifySchedulerService;
import com.kanezheng.app.jobmanagement.service.quartzjob.EmailNotifySchedulerServiceImpl;
import com.kanezheng.app.jobmanagement.service.schedule.ScheduleService;
import javafx.scene.Scene;
import org.quartz.Scheduler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

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

        logger.info("[Schedule CRUD] Request to create a schedule, schedule info = {}", schedule);

        Schedule scheduleResp = scheduleService.createSchedule(schedule);

        int result = emailNotifySchedulerService.createEmailNotifyJob("kane", dashboardId, schedule);

        if (0 == result){
            logger.info("[Schedule CRUD] Create schedule success, schedule info: {}", scheduleResp);
        }

        return scheduleResp;
    }

    @GetMapping("/schedule")
    public Schedule getSchedule(@PathVariable Long dashboardId, @PathVariable String widgetId) throws Exception {


/*        Schedule schedule = scheduleService.getScheduleByWidgetId(widgetId);
        if (schedule == null){
            throw new ScheduleNotFoundException();
        }
        return schedule;*/

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

        emailNotifySchedulerService.deleteEmailNotifyJob("kane", dashboardId, widgetId, scheduleId);

        scheduleService.deleteSchedule(scheduleId);
    }

    @DeleteMapping("/schedule")
    public void deleteSchedule(@PathVariable Long dashboardId,
                               @PathVariable String widgetId) throws Exception{
        scheduleService.deleteScheduleByWidgetId(widgetId);
    }
}
