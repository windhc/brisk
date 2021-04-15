package com.windhc.brisk;

import com.windhc.brisk.ioc.annotation.Bean;
import com.windhc.brisk.ioc.annotation.Inject;
import com.windhc.brisk.scheduling.annotation.Scheduled;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;

/**
 * @author windhc
 */
@Bean
public class TestSchedule {
    private static final Logger logger = LoggerFactory.getLogger(TestSchedule.class);

    @Inject
    private TestService testService;

    @Scheduled(cron = "0/3 * * * * ?")
    public void test1() {
        logger.info(testService.t());
        logger.info("TestBean test1 " + LocalDateTime.now());
    }

}
