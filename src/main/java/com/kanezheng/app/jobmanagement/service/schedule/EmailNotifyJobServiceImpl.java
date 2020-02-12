package com.kanezheng.app.jobmanagement.service.schedule;

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
    public EmailNotifyJobEntity getEmailNotifyJob() {

        EmailNotifyJobEntity nextWaitingJob = emailNotifyJobRepository.findNextWaitingJob();
        logger.info("Get the next waiting job: {}", nextWaitingJob);
        return nextWaitingJob;
    }

    @Override
    public void updateEmailNofifyJobStatus(EmailNotifyJobRest emailNotifyJobRest) {
        emailNotifyJobRepository.findById(emailNotifyJobRest.getId())
                .map(emailNotifyJobEntity -> {
                    emailNotifyJobEntity.setStatus(emailNotifyJobRest.getStatus());
                    return emailNotifyJobRepository.save(emailNotifyJobEntity);
                }).orElseThrow(() -> new ResourceNotFoundException("Email notify job not found with id " + emailNotifyJobRest.getId()));

        return ;
    }
}
