package com.kanezheng.app.jobmanagement.util;

import com.cronutils.builder.CronBuilder;
import com.cronutils.descriptor.CronDescriptor;
import com.cronutils.model.Cron;
import com.cronutils.model.CronType;
import com.cronutils.model.definition.CronDefinitionBuilder;
import com.cronutils.parser.CronParser;
import com.kanezheng.app.jobmanagement.dao.schedule.Schedule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.OffsetDateTime;
import java.util.Locale;

import static com.cronutils.model.field.expression.FieldExpressionFactory.*;
import static org.quartz.DateBuilder.MONDAY;
import static org.quartz.DateBuilder.WEDNESDAY;

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
        }

        return cronExpression;
    }

    private static String generateDailyExpression(Schedule schedule) {

        OffsetDateTime initialDeliverTime = schedule.getInitialDeliverTime();

        Cron cron = CronBuilder.cron(CronDefinitionBuilder.instanceDefinitionFor(CronType.QUARTZ))
                .withDoW(questionMark())
                .withMonth(always())
                .withDoM(always())
                .withHour(on(initialDeliverTime.getHour()))
                .withMinute(on(initialDeliverTime.getMinute()))
                .withSecond(on(0))
                .instance();

        String cronExpression = cron.asString();

        logger.info("[Schedule CRUD] Generate DAILY cron expression: {}", cronExpression);

        return cronExpression;
    }

    private static String generateWeeklyExpression(Schedule schedule) {
        OffsetDateTime initialDeliverTime = schedule.getInitialDeliverTime();

        Cron cron = CronBuilder.cron(CronDefinitionBuilder.instanceDefinitionFor(CronType.QUARTZ))
                .withDoW(on(initialDeliverTime.getDayOfWeek().getValue()))
                .withMonth(always())
                .withDoM(always())
                .withHour(on(initialDeliverTime.getHour()))
                .withMinute(on(initialDeliverTime.getMinute()))
                .withSecond(on(0))
                .instance();

        String cronExpression = cron.asString();

        logger.info("[Schedule CRUD] Generate WEEKLY cron expression: {}", cronExpression);

        return cronExpression;
    }

    private static String generateMonthlyExpression(Schedule schedule) {
        OffsetDateTime initialDeliverTime = schedule.getInitialDeliverTime();

        Cron cron = CronBuilder.cron(CronDefinitionBuilder.instanceDefinitionFor(CronType.QUARTZ))
                .withDoW(questionMark())
                .withMonth(always())
                .withDoM(on(initialDeliverTime.getDayOfMonth()))
                .withHour(on(initialDeliverTime.getHour()))
                .withMinute(on(initialDeliverTime.getMinute()))
                .withSecond(on(0))
                .instance();

        String cronExpression = cron.asString();

        logger.info("[Schedule CRUD] Generate MONTHLY cron expression: {}", cronExpression);

        return cronExpression;
    }

    private static String generateWeekDayExpression(Schedule schedule) {

        OffsetDateTime initialDeliverTime = schedule.getInitialDeliverTime();

        //In Quartz, Sunday is 1
        Cron cron = CronBuilder.cron(CronDefinitionBuilder.instanceDefinitionFor(CronType.QUARTZ))
                .withDoW(between(2, 6))
                .withMonth(always())
                .withDoM(questionMark())
                .withHour(on(initialDeliverTime.getHour()))
                .withMinute(on(initialDeliverTime.getMinute()))
                .withSecond(on(0))
                .instance();

        String cronExpression = cron.asString();

        logger.info("[Schedule CRUD] Generate WEEKDAY cron expression: {}", cronExpression);
        return cronExpression;
    }

    private static String generateCustomExpression(Schedule schedule) {
        String cronExpression = null;

        switch (schedule.getCustomRepeatType()){
/*            case DAYS:
                cronExpression = generateCustomDaysExpression(schedule);
                break;*/
            case WEEKS:
                cronExpression = generateCustomWeekdaysExpression(schedule);
                break;
            case MONTHS:
                cronExpression = generateCustomMonthsExpression(schedule);
                break;
        }

        return cronExpression;
    }

    //should use simple trigger, N * 24 hour, would be more accurate
/*    private static String generateCustomDaysExpression(Schedule schedule) {

        return null;
    }*/

    private static String generateCustomWeekdaysExpression(Schedule schedule) {
        OffsetDateTime initialDeliverTime = schedule.getInitialDeliverTime();

        //e.g. "0 0 13 ? * MON,WED/2"   from 13:00:00 on Monday, Wednesday, trigger every 2 weeks
        Cron cron = CronBuilder.cron(CronDefinitionBuilder.instanceDefinitionFor(CronType.QUARTZ))
                .withDoW(every(on(MONDAY).and(on(WEDNESDAY)), 2))
                .withMonth(always())
                .withDoM(questionMark())
                .withHour(on(initialDeliverTime.getHour()))
                .withMinute(on(initialDeliverTime.getMinute()))
                .withSecond(on(0))
                .instance();

        String cronExpression = cron.asString();

        logger.info("[Schedule CRUD] Generate CUSTOM_ON_WEEKDAYS cron expression: {}", cronExpression);
        return cronExpression;
    }

    private static String generateCustomMonthsExpression(Schedule schedule) {
        OffsetDateTime initialDeliverTime = schedule.getInitialDeliverTime();

        //e.g. "0 0 13 7 2/3 ?" ==> from 13:00:00 7 Feb, trigger every 3 month
        Cron cron = CronBuilder.cron(CronDefinitionBuilder.instanceDefinitionFor(CronType.QUARTZ))
                .withDoW(questionMark())
                .withMonth(every(on(initialDeliverTime.getMonthValue()), schedule.getCustomRepeatValue()))
                .withDoM(questionMark())
                .withHour(on(initialDeliverTime.getHour()))
                .withMinute(on(initialDeliverTime.getMinute()))
                .withSecond(on(0))
                .instance();

        String cronExpression = cron.asString();

        logger.info("[Schedule CRUD] Generate CUSTOM_ON_MONTHS cron expression: {}", cronExpression);
        return cronExpression;
    }
}
