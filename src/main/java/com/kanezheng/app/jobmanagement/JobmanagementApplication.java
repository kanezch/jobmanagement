package com.kanezheng.app.jobmanagement;

import com.kanezheng.app.jobmanagement.config.scheduler.SchedulerConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@Import({ SchedulerConfig.class })
@SpringBootApplication
public class JobmanagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(JobmanagementApplication.class, args);
	}

}
