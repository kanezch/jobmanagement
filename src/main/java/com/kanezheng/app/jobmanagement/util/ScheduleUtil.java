package com.kanezheng.app.jobmanagement.util;

import com.cronutils.builder.CronBuilder;
import com.cronutils.descriptor.CronDescriptor;
import com.cronutils.model.Cron;
import com.cronutils.model.CronType;
import com.cronutils.model.definition.CronDefinitionBuilder;
import com.cronutils.model.field.value.SpecialChar;
import com.cronutils.parser.CronParser;
import com.kanezheng.app.jobmanagement.dao.schedule.Schedule;
import com.kanezheng.app.jobmanagement.service.quartzjob.EmailNotifySchedulerServiceImpl;
import com.kanezheng.app.jobmanagement.service.schedule.ScheduleServiceImpl;
import com.sun.scenario.effect.Offset;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.OffsetDateTime;
import java.util.Locale;

import static com.cronutils.model.field.expression.FieldExpressionFactory.*;

public class ScheduleUtil {
    private final static Logger logger = LoggerFactory.getLogger(ScheduleUtil.class);

    public static String composeEmailNotifyJobName(String userName,
                                                   Long dashboardId,
                                                   String widgetId,
                                                   Long scheduleId){
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

    public static String translateCronExpressToHumanReadableTxt(String cronExpression){

        CronParser parser = new CronParser(CronDefinitionBuilder.instanceDefinitionFor(CronType.QUARTZ));
        Cron cron = parser.parse(cronExpression);
        final CronDescriptor descriptor = CronDescriptor.instance(Locale.UK);
        return descriptor.describe(cron);
    }

    public static String composeCronExpression(Schedule schedule){

        String cronExpression = null;

        switch (schedule.getScheduleRepeatType()){

            case DAILY:
                cronExpression = generateDailyExpression(schedule);
                break;
            case WEEKLY:
                cronExpression = generateWeeklyExpression(schedule);
                break;
            case MONTHLY:
                cronExpression = generateMonthlyExpression(schedule);
                break;
            case WEEKDAY:
                cronExpression = generateWeekDayExpression(schedule);
                break;
            case CUSTOM:
                cronExpression = generateCustomExpression(schedule);
                break;
            default:
                break;
        }

        return cronExpression;
    }

    private static String generateDailyExpression(Schedule schedule) {
        String cronExpression = null;

        OffsetDateTime firstTriggerTime = schedule.getInitialDeliverTime();


        Cron cron = CronBuilder.cron(CronDefinitionBuilder.instanceDefinitionFor(CronType.QUARTZ))
                .withDoW(questionMark())
                .withMonth(always())
                .withDoM(always())
                .withHour(on(firstTriggerTime.getHour()))
                .withMinute(on(firstTriggerTime.getMinute()))
                .withSecond(on(0))
                .instance();

        cronExpression = cron.asString();

        logger.info("[Schedule CRUD] Generate DAILY cron expression: {}", cronExpression);

        return cronExpression;
    }

    private static String generateWeeklyExpression(Schedule schedule) {
        return "";
    }

    private static String generateMonthlyExpression(Schedule schedule) {
        return "";
    }

    private static String generateWeekDayExpression(Schedule schedule) {
        return "";
    }

    private static String generateCustomExpression(Schedule schedule) {
        return "";
    }
}
