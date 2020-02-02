package com.kanezheng.app.jobmanagement.service.quartzjob;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class SimpleService {
    private static final Logger logger = LoggerFactory.getLogger(SimpleService.class);

    public void processData() {
        logger.info("Hello World!");
    }
}