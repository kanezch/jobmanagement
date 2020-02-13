package com.kanezheng.app.jobmanagement.service.schedule;

import com.kanezheng.app.jobmanagement.dao.schedule.Schedule;
import com.kanezheng.app.jobmanagement.exception.ResourceNotFoundException;
import com.kanezheng.app.jobmanagement.repository.schedule.ScheduleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Service
public class ScheduleServiceImpl implements ScheduleService {

	Logger logger = LoggerFactory.getLogger(ScheduleServiceImpl.class);

	@Autowired
	ScheduleRepository scheduleRepository;

	@Override
	public Schedule createSchedule(Schedule scheduleReq) {

		return scheduleRepository.save(scheduleReq);
	}

	@Override
	public Schedule getScheduleByWidgetId(String widgetId) {
		return scheduleRepository.findByWidgetId(widgetId);
	}

	@Override
	public Schedule getScheduleById(Long scheduleId) throws EntityNotFoundException{
		return scheduleRepository.findById(scheduleId)
				.orElseThrow(() -> new EntityNotFoundException("Schedule not exist"));
	}

	@Override
	public Schedule updateSchedule(Long scheduleId, Schedule newSchedule) {

		return scheduleRepository.findById(scheduleId)
				.map(schedule -> {
					schedule.setScheduleName(newSchedule.getScheduleName());
					schedule.setEmailRecipients(newSchedule.getEmailRecipients());
					schedule.setIncludeCustomMessage(newSchedule.getIncludeCustomMessage());
					schedule.setCustomMessage(newSchedule.getCustomMessage());
					schedule.setInitialDeliverTime(newSchedule.getInitialDeliverTime());
					schedule.setScheduleRepeatType(newSchedule.getScheduleRepeatType());
					schedule.setCustomRepeatType(newSchedule.getCustomRepeatType());
					schedule.setCustomRepeatValue(newSchedule.getCustomRepeatValue());
					schedule.setIncludeEndTime(newSchedule.getIncludeEndTime());
					schedule.setScheduleEndTime(newSchedule.getScheduleEndTime());
					schedule.setCustomRepeatOnWeekdays(newSchedule.getCustomRepeatOnWeekdays());

					return scheduleRepository.save(schedule);
				}).orElseThrow(() -> new ResourceNotFoundException("Schedule not found with id " + scheduleId));
	}

	@Override
	public void deleteSchedule(Long scheduleId) {
		scheduleRepository.deleteById(scheduleId);
	}

	@Override
	public void deleteScheduleByWidgetId(String widgetId){
		scheduleRepository.deleteByWidgetId(widgetId);
	}
}
