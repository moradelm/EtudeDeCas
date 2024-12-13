package org.example.hotelreservationcas.config;

import io.micrometer.core.aop.TimedAspect;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MetricsConfig {

    @Bean
    public TimedAspect metricsTimedAspect(MeterRegistry registry) {
        return new TimedAspect(registry);
    }
}