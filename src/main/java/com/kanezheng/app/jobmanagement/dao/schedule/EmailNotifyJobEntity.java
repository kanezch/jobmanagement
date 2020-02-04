package com.kanezheng.app.jobmanagement.dao.schedule;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

enum EmailJobStatus {
    STANDBY,
    PROCESSING,
    SUCCESS,
    FAILED
}

@Table(schema = "portal", name = "email_notify_jobs_queue")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmailNotifyJobEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userName;

    private Long dashboardId;

    private String widgetId;

    private String scheduleId;

    private  EmailJobStatus status;

}
