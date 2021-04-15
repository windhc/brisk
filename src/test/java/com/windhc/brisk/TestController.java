package com.windhc.brisk;

import com.windhc.brisk.mvc.annotation.Controller;
import com.windhc.brisk.mvc.annotation.Get;
import com.windhc.brisk.scheduling.annotation.Scheduled;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;

/**
 * @author windhc
 */
@Controller("/test")
public class TestController {
    private static final Logger logger = LoggerFactory.getLogger(TestController.class);

    @Get("/a")
    public String a() {
        return "a";
    }

    @Get("/b")
    public String b() {
        return "b";
    }

    @Scheduled(cron = "0/2 * * * * ?")
    public void c() {
        logger.info("TestController c {}", LocalDateTime.now());
    }
}
