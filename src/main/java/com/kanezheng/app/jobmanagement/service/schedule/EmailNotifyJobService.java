package com.kanezheng.app.jobmanagement.service.schedule;

import com.kanezheng.app.jobmanagement.dao.schedule.EmailNotifyJobEntity;
import com.kanezheng.app.jobmanagement.dao.schedule.EmailNotifyJobRest;

public interface EmailNotifyJobService {

    EmailNotifyJobEntity getEmailNotifyJob() throws Exception;
    void updateEmailNofifyJobStatus(EmailNotifyJobRest emailNotifyJobRest) throws Exception;

}