package com.kanezheng.app.jobmanagement.service.schedule;

import com.kanezheng.app.jobmanagement.dao.schedule.Schedule;
import com.kanezheng.app.jobmanagement.exception.ResourceNotFoundException;
import com.kanezheng.app.jobmanagement.repository.schedule.ScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class ScheduleServiceImpl implements ScheduleService {

	@Autowired
	ScheduleRepository scheduleRepository;

	@Override
	public Schedule createSchedule(Schedule schedule) throws Exception {
		return scheduleRepository.save(schedule);
	}

	@Override
	public Schedule getScheduleByWidgetId(Long widgetId) throws Exception {
		return scheduleRepository.findByWidgetId(widgetId);
	}

	@Override
	public Schedule updateSchedule(Long scheduleId, Schedule newSchedule) throws Exception {
		return scheduleRepository.findById(scheduleId)
				.map(schedule -> {
					schedule.setScheduleName(newSchedule.getScheduleName());
					schedule.setCustomMessage(newSchedule.getCustomMessage());
					schedule.setEmailRecipients(newSchedule.getEmailRecipients());
					schedule.setCustomSchedulePeriod(newSchedule.getCustomSchedulePeriod());
					schedule.setIncludeCustomMessage(newSchedule.getIncludeCustomMessage());
					schedule.setInitialDeliverTime(newSchedule.getInitialDeliverTime());
					schedule.setSchedulePeriodType(newSchedule.getSchedulePeriodType());
					schedule.setIncludeEndTime(newSchedule.getIncludeEndTime());
					schedule.setScheduleEndTime(newSchedule.getScheduleEndTime());

					return scheduleRepository.save(schedule);
				}).orElseThrow(() -> new ResourceNotFoundException("Schedule not found with id " + scheduleId));
	}

	@Override
	public ResponseEntity deleteSchedule(Long scheduleId) throws Exception {
		return scheduleRepository.findById(scheduleId)
				.map(schedule -> {
					scheduleRepository.delete(schedule);
					return ResponseEntity.noContent().build();
				}).orElseThrow(() -> new ResourceNotFoundException("Schedule not found with id " + scheduleId));
	}
}
