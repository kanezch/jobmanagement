package com.kanezheng.app.jobmanagement.service.schedule;

import com.kanezheng.app.jobmanagement.dao.schedule.Schedule;

public interface ScheduleService {

    Schedule createSchedule(Schedule schedule) throws Exception;

    Schedule getScheduleByWidgetId(String widgetId) throws Exception;

    Schedule updateSchedule(Long scheduleId, Schedule newSchedule) throws Exception;

    void deleteSchedule(Long scheduleId) throws Exception;

    void deleteScheduleByWidgetId(String widgetId) throws Exception;

}
