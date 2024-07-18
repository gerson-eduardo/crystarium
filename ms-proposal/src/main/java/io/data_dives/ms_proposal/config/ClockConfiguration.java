package io.data_dives.ms_proposal.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Clock;

@Configuration
public class ClockConfiguration {

    @Bean
    public Clock clock(){
        return Clock.systemDefaultZone();
    }
}
