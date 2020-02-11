package com.kanezheng.app.jobmanagement.config.scheduler;

import org.quartz.Scheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import javax.sql.DataSource;

@Configuration
public class QuartzSchedulerConfiguration {

    @Autowired
    ApplicationContext applicationContext;

    @Bean
    public SchedulerFactoryBean schedulerFactory(DataSource dataSource) {

        SchedulerFactoryBean schedulerFactory = new SchedulerFactoryBean();
        schedulerFactory.setSchedulerName("scheduler_kane");
        schedulerFactory.setConfigLocation(new ClassPathResource("quartz.properties"));
        schedulerFactory.setDataSource(dataSource);

        AutowiringSpringBeanJobFactory jobFactory = new AutowiringSpringBeanJobFactory();
        jobFactory.setApplicationContext(applicationContext);
        schedulerFactory.setJobFactory(jobFactory);

        return schedulerFactory;
    }

    @Bean
    public Scheduler scheduler(SchedulerFactoryBean schedulerFactory){
        Scheduler scheduler = schedulerFactory.getScheduler();
        System.out.println("#######scheduler start!!!@###$$$");
        return scheduler;
    }
}
