package com.kanezheng.app.jobmanagement.service.schedule;

import com.kanezheng.app.jobmanagement.dao.schedule.EmailJobStatus;
import com.kanezheng.app.jobmanagement.dao.schedule.EmailNotifyJobEntity;
import com.kanezheng.app.jobmanagement.dao.schedule.EmailNotifyJobRest;
import com.kanezheng.app.jobmanagement.exception.ResourceNotFoundException;
import com.kanezheng.app.jobmanagement.repository.schedule.EmailNotifyJobRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmailNotifyJobServiceImpl implements EmailNotifyJobService {

    Logger logger = LoggerFactory.getLogger(EmailNotifyJobServiceImpl.class);

    @Autowired
    EmailNotifyJobRepository emailNotifyJobRepository;

    @Override
    public EmailNotifyJobEntity getEmailNotifyJob() throws Exception {

/*        EmailNotifyJobEntity emailNotifyJobEntity = EmailNotifyJobEntity.builder().userName("kane")
                .dashboardId(0L)
                .widgetId("1234-5678")
                .scheduleId(0L)
                .status(EmailJobStatus.STANDBY)
                .build();*/

        EmailNotifyJobEntity nextWaitingJob = emailNotifyJobRepository.findNextWaitingJob();

        logger.info("this is the next job: {}", nextWaitingJob);

        return nextWaitingJob;
    }

    @Override
    public void updateEmailNofifyJobStatus(EmailNotifyJobRest emailNotifyJobRest) throws Exception {
        emailNotifyJobRepository.findById(emailNotifyJobRest.getId())
                .map(emailNotifyJobEntity -> {
                    emailNotifyJobEntity.setStatus(emailNotifyJobRest.getStatus());
                    return emailNotifyJobRepository.save(emailNotifyJobEntity);
                }).orElseThrow(() -> new ResourceNotFoundException("Email notify job not found with id " + emailNotifyJobRest.getId()));

        return ;
    }
}
