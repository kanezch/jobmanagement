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

enum ScheduleRepeatType {
    ONE_OFF,
    DAILY,
    WEEKLY,
    MONTHLY,
    WEEKDAY,
    CUSTOM
}

enum CustomRepeatType {
    DAILY,
    WEEKLY,
    MONTHLY,
}

enum WeekDays {
    MON,TUE,WED,THU,FRI,SAT,SUN
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
    @Column(name = "schedule_repeat_type")
    private ScheduleRepeatType scheduleRepeatType;

    @Column(name = "custom_repeat_type")
    private CustomRepeatType customRepeatType;

    @Column(name = "custom_repeat_value")
    private Integer customRepeatValue;

    @ElementCollection
    @CollectionTable(schema = "portal", name = "custom_repeat_weekdays", joinColumns = @JoinColumn(name = "schedule_id"))
    private Set<WeekDays> customRepeatOnWeekdays = new HashSet<>();

    @NotNull
    @Column(name = "include_end_time")
    private Boolean includeEndTime;

    @Column(name = "schedule_end_time", columnDefinition = "TIMESTAMP WITH TIME ZONE")
    private OffsetDateTime scheduleEndTime;
}
