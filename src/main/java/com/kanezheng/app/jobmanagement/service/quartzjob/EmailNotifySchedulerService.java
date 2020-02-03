package com.kanezheng.app.jobmanagement.service.quartzjob;

import com.kanezheng.app.jobmanagement.dao.schedule.Schedule;

public interface EmailNotifySchedulerService {
    public int createEmailNotifyJob(Schedule schedule) throws Exception;

    public int updateEmailNotifyJob(Schedule schedule) throws Exception;

    public void deleteEmailNotifyJob() throws Exception;
}
