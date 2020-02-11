package com.kanezheng.app.jobmanagement.repository.schedule;

import com.kanezheng.app.jobmanagement.dao.schedule.EmailNotifyJobEntity;


public interface EmailNotifyJobRepositoryCustom {

    EmailNotifyJobEntity findNextWaitingJob();
}
