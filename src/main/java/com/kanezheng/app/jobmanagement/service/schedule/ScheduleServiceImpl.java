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
	public Schedule getScheduleByWidgetId(String widgetId) throws Exception {
		return scheduleRepository.findByWidgetId(widgetId);
	}

	@Override
	public Schedule updateSchedule(Long scheduleId, Schedule newSchedule) throws Exception {
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

/*	@Override 与下面的代码对比，建议用jpa 的 derived delete query
	public void deleteScheduleByWidgetId(String widgetId) throws Exception{
		Schedule schedule= scheduleRepository.findByWidgetId(widgetId);
		if (null != schedule){
			scheduleRepository.delete(schedule);
		}
	}*/

	@Override
	public void deleteScheduleByWidgetId(String widgetId){
		scheduleRepository.deleteByWidgetId(widgetId);
	}
}
