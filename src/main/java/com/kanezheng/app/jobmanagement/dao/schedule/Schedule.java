package com.kanezheng.app.jobmanagement.dao.schedule;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.OffsetDateTime;
import java.util.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

enum SchedulePeriod {
    ONE_OFF,
    DAILY,
    WEEKLY,
    MONTHLY,
    WEEKDAY,
    CUSTOM
}

@Table(schema = "portal", name = "schedule")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Schedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "serial")
    private Long id;

    @NotNull
    @Column(name = "widget_id", unique = true)
    private String widgetId;

    @NotBlank
    @Column(name = "schedule_name", columnDefinition = "text")
    private String scheduleName;

/*    @Column(name = "email_recipients")
    @ElementCollection
    private Set<String> emailRecipients = new HashSet();*/

    @Column(name = "include_custom_message")
    private Boolean includeCustomMessage;

    @Column(name = "custom_message", columnDefinition = "text")
    private String customMessage;

    @Column(name = "initial_deliverTime", columnDefinition = "TIMESTAMP WITH TIME ZONE")
    private OffsetDateTime initialDeliverTime;

    @Column(name = "schedule_period_type")
    private SchedulePeriod schedulePeriodType;

    @Column(name = "custom_schedule_period")
    private Integer customSchedulePeriod;

    @Column(name = "include_end_time")
    private Boolean includeEndTime;

    @Column(name = "schedule_end_time", columnDefinition = "TIMESTAMP WITH TIME ZONE")
    private OffsetDateTime scheduleEndTime;
}
