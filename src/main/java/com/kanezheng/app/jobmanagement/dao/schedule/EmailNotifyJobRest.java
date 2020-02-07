package com.kanezheng.app.jobmanagement.dao.schedule;

import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmailNotifyJobRest {
    private Long id;
    private String userName;
    private Long dashboardId;
    private String widgetId;
    private Long scheduleId;
    private EmailJobStatus status;
    private Set<String> emailRecipients;
}
