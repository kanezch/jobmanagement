package com.kanezheng.app.jobmanagement.service.schedule;

import com.kanezheng.app.jobmanagement.dao.schedule.Schedule;
import org.springframework.http.ResponseEntity;

public interface ScheduleService {

    public Schedule createSchedule(Schedule schedule) throws Exception;

    public Schedule getScheduleByWidgetId(Long widgetId) throws Exception;

    public Schedule updateSchedule(Long scheduleId, Schedule newSchedule) throws Exception;

    public ResponseEntity deleteSchedule(Long scheduleId) throws Exception;
}
