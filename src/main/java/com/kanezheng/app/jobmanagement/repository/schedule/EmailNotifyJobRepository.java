package com.kanezheng.app.jobmanagement.repository.schedule;

import com.kanezheng.app.jobmanagement.dao.schedule.EmailNotifyJobEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmailNotifyJobRepository extends CrudRepository<EmailNotifyJobEntity, Long>, EmailNotifyJobRepositoryCustom{

}
