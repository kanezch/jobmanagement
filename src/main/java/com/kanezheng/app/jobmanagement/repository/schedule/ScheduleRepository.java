package com.kanezheng.app.jobmanagement.repository.schedule;

import com.kanezheng.app.jobmanagement.dao.schedule.Schedule;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface ScheduleRepository extends CrudRepository<Schedule, Long> {
    public Schedule findByWidgetId(String WidgetId);

    @Transactional
    public void deleteByWidgetId(String WidgetId);
}
