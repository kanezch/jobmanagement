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
    private Long id;

    @NotNull
    @Column(name = "widget_id")
    private String widgetId;

    @NotBlank
    @Column(name = "schedule_name", columnDefinition = "text")
    private String scheduleName;

    @ElementCollection
    @CollectionTable(schema = "portal", name = "schedule_email_recipients", joinColumns = @JoinColumn(name = "schedule_id"))
    private Set<String> emailRecipients = new HashSet<>();

    @NotNull
    @Column(name = "include_custom_message")
    private Boolean includeCustomMessage;

    @Column(name = "custom_message", columnDefinition = "text")
    private String customMessage;

    @NotNull
    @Column(name = "initial_deliver_time", columnDefinition = "TIMESTAMP WITH TIME ZONE")
    private OffsetDateTime initialDeliverTime;

    @NotNull
    @Column(name = "schedule_period_type")
    private SchedulePeriod schedulePeriodType;

    @Column(name = "custom_schedule_period")
    private Integer customSchedulePeriod;

    @NotNull
    @Column(name = "include_end_time")
    private Boolean includeEndTime;

    @Column(name = "schedule_end_time", columnDefinition = "TIMESTAMP WITH TIME ZONE")
    private OffsetDateTime scheduleEndTime;
}
