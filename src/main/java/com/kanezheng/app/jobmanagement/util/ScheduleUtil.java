package com.kanezheng.app.jobmanagement.util;

public class ScheduleUtil {
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
}
