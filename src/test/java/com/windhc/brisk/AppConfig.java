package com.windhc.brisk;

import com.windhc.brisk.ioc.annotation.Bean;
import com.windhc.brisk.ioc.annotation.Configuration;

/**
 * @author hc
 */
@Configuration
public class AppConfig {

    @Bean
    public TestService testService() {
        return new TestService();
    }

}
