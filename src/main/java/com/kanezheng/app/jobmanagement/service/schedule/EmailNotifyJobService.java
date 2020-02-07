package com.kanezheng.app.jobmanagement.service.schedule;

import com.kanezheng.app.jobmanagement.dao.schedule.EmailNotifyJobEntity;
import com.kanezheng.app.jobmanagement.dao.schedule.EmailNotifyJobRest;
import com.kanezheng.app.jobmanagement.dao.schedule.Schedule;


public interface EmailNotifyJobService {

    public EmailNotifyJobEntity getEmailNotifyJob() throws Exception;
    public void updateEmailNofifyJobStatus(EmailNotifyJobRest emailNotifyJobRest) throws Exception;

}