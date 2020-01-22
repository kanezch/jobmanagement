package com.kanezheng.app.jobmanagement.controller.schedule;

import com.kanezheng.app.jobmanagement.dao.schedule.Schedule;
import com.kanezheng.app.jobmanagement.service.schedule.ScheduleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class ScheduleController {
    Logger logger = LoggerFactory.getLogger(ScheduleController.class);

    @Autowired
    private ScheduleService scheduleService;

    @PostMapping("/schedule")
    public Schedule createSchedule(@Valid @RequestBody Schedule schedule) throws Exception{
        return scheduleService.createSchedule(schedule);
    }

    @GetMapping("/schedule")
    public Schedule getSchedule(@RequestParam(value = "widgetId")Long widgetId) throws Exception{

        return scheduleService.getScheduleByWidgetId(widgetId);
    }

    @PutMapping("/schedule/{scheduleId}")
    public Schedule updateSchedule(@PathVariable Long scheduleId,
                                   @Valid @RequestBody Schedule scheduleRequest) throws Exception{
        return scheduleService.updateSchedule(scheduleId, scheduleRequest);
    }

    @DeleteMapping("/schedule/{scheduleId}")
    public ResponseEntity<?> deleteSchedule(@PathVariable Long scheduleId) throws Exception{
        return scheduleService.deleteSchedule(scheduleId);
    }
}
