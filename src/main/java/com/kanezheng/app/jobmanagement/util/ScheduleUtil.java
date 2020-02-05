package com.kanezheng.app.jobmanagement.util;

import com.cronutils.builder.CronBuilder;
import com.cronutils.model.Cron;
import com.cronutils.model.CronType;
import com.cronutils.model.definition.CronDefinitionBuilder;
import com.cronutils.model.field.value.SpecialChar;
import com.kanezheng.app.jobmanagement.dao.schedule.Schedule;
import com.kanezheng.app.jobmanagement.service.quartzjob.EmailNotifySchedulerServiceImpl;
import com.kanezheng.app.jobmanagement.service.schedule.ScheduleServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.cronutils.model.field.expression.FieldExpressionFactory.*;

public class ScheduleUtil {
    private final static Logger logger = LoggerFactory.getLogger(ScheduleUtil.class);

    public static String composeEmailNotifyJobName(String userName,
                                                   Long dashboardId,
                                                   String widgetId,
                                                   Long scheduleId){
        logger.info("composeEmailNotifyJobName:{}，{}，{}，{}", userName, dashboardId, widgetId, scheduleId);

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(userName)
                     .append("-")
                     .append(dashboardId)
                     .append("-")
                     .append(widgetId)
                     .append("-")
                     .append(scheduleId);
        return stringBuilder.toString();
    }

    public static String composeCronExpression(Schedule schedule){

        logger.info("###Before composeCronExpression");

        Cron cron = CronBuilder.cron(CronDefinitionBuilder.instanceDefinitionFor(CronType.QUARTZ))
                .withYear(always())
                .withDoM(between(SpecialChar.L, 3))
                .withMonth(always())
                .withDoW(questionMark())
                .withHour(always())
                .withMinute(always())
                .withSecond(on(0))
                .instance();
// Obtain the string expression
        String cronAsString = cron.asString(); // 0 * * L-3 * ? *

        logger.info("###After composeCronExpression: {}", cronAsString);

        return cronAsString;
    }
}
