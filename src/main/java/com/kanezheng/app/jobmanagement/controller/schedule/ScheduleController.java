package com.kanezheng.app.jobmanagement.controller.schedule;

import com.kanezheng.app.jobmanagement.dao.schedule.Schedule;
import com.kanezheng.app.jobmanagement.service.quartzjob.EmailNotifySchedulerService;
import com.kanezheng.app.jobmanagement.service.schedule.ScheduleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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

        //Save the schedule setting
        Schedule scheduleResp = scheduleService.createSchedule(schedule);

        //Schedule a job
        int result = emailNotifySchedulerService.createEmailNotifyJob("kane", dashboardId, schedule);

        if (0 == result){
            logger.info("[Schedule CRUD] Create schedule success, schedule info: {}", scheduleResp);
        }

        return scheduleResp;
    }

    @GetMapping("/schedule")
    public Schedule getSchedule(@PathVariable Long dashboardId, @PathVariable String widgetId) throws Exception {

        return scheduleService.getScheduleByWidgetId(widgetId);
    }

    @PutMapping("/schedule/{scheduleId}")
    public Schedule updateSchedule(@PathVariable Long dashboardId,
                                   @PathVariable String widgetId,
                                   @PathVariable Long scheduleId,
                                   @Valid @RequestBody Schedule scheduleRequest) throws Exception{

        Schedule oldScheduleSetting = scheduleService.getScheduleById(scheduleRequest.getId());

        if (bNeedToRescheduleJob(oldScheduleSetting, scheduleRequest)){

            //Hard coded
            String userName = "kane";
            emailNotifySchedulerService.updateEmailNotifyJob(userName, dashboardId, widgetId, scheduleRequest);
        }

        return scheduleService.updateSchedule(scheduleId, scheduleRequest);
    }

    private boolean bNeedToRescheduleJob(Schedule oldScheduleSetting, Schedule scheduleRequest) {

        if (!oldScheduleSetting.getInitialDeliverTime().isEqual(scheduleRequest.getInitialDeliverTime()) ||
                oldScheduleSetting.getScheduleRepeatType() != scheduleRequest.getScheduleRepeatType() ||
                oldScheduleSetting.getCustomRepeatType() != scheduleRequest.getCustomRepeatType() ||
                oldScheduleSetting.getCustomRepeatValue() != scheduleRequest.getCustomRepeatValue() ||
                oldScheduleSetting.getCustomRepeatOnWeekdays() != scheduleRequest.getCustomRepeatOnWeekdays() ||
                oldScheduleSetting.getIncludeEndTime() != scheduleRequest.getIncludeEndTime() ||
                oldScheduleSetting.getScheduleEndTime() != scheduleRequest.getScheduleEndTime()){
            return true;
        }

        return false;
    }

    @DeleteMapping("/schedule/{scheduleId}")
    public void deleteSchedule(@PathVariable Long dashboardId,
                               @PathVariable String widgetId,
                               @PathVariable Long scheduleId) throws Exception{

        //Hard coded
        String userName = "kane";
        emailNotifySchedulerService.deleteEmailNotifyJob(userName, dashboardId, widgetId, scheduleId);

        scheduleService.deleteSchedule(scheduleId);
    }

    @DeleteMapping("/schedule")
    public void deleteSchedule(@PathVariable Long dashboardId,
                               @PathVariable String widgetId) throws Exception{
        scheduleService.deleteScheduleByWidgetId(widgetId);
    }
}
