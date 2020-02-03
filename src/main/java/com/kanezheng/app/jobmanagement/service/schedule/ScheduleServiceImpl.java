package com.kanezheng.app.jobmanagement.service.schedule;

import com.kanezheng.app.jobmanagement.controller.schedule.ScheduleController;
import com.kanezheng.app.jobmanagement.dao.schedule.Schedule;
import com.kanezheng.app.jobmanagement.exception.ResourceNotFoundException;
import com.kanezheng.app.jobmanagement.repository.schedule.ScheduleRepository;
import com.kanezheng.app.jobmanagement.service.quartzjob.EmailNotifySchedulerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class ScheduleServiceImpl implements ScheduleService {

	Logger logger = LoggerFactory.getLogger(ScheduleServiceImpl.class);

	@Autowired
	ScheduleRepository scheduleRepository;

	@Autowired
	private EmailNotifySchedulerService emailNotifySchedulerService;

	@Override
	public Schedule createSchedule(Schedule scheduleReq) throws Exception {

		int result = emailNotifySchedulerService.createEmailNotifyJob(scheduleReq);
		if (result == 0){
			Schedule scheduleResp = scheduleRepository.save(scheduleReq);
			logger.info("Create schedule in the schedule service!");
			return scheduleResp;
		}

		return null;
	}

	@Override
	public Schedule getScheduleByWidgetId(String widgetId) throws Exception {
		return scheduleRepository.findByWidgetId(widgetId);
	}

	@Override
	public Schedule updateSchedule(Long scheduleId, Schedule newSchedule) throws Exception {

		emailNotifySchedulerService.updateEmailNotifyJob(newSchedule);
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

		try {
			emailNotifySchedulerService.deleteEmailNotifyJob();
		} catch (Exception e) {
			e.printStackTrace();
		}
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
