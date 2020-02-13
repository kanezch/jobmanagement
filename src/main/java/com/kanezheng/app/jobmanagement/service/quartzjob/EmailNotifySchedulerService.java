package com.kanezheng.app.jobmanagement.service.quartzjob;

import com.kanezheng.app.jobmanagement.dao.schedule.Schedule;

public interface EmailNotifySchedulerService {
    int createEmailNotifyJob(String userName, Long dashboardId, Schedule schedule) throws Exception;

    int updateEmailNotifyJob(String userName, Long dashboardId, String widgetId, Schedule schedule) throws Exception;

    void deleteEmailNotifyJob(String userName, Long dashboardId, String widgetId, Long scheduleId) throws Exception;
}
