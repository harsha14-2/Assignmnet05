package com.a5.config;

import io.micrometer.core.aop.TimedAspect;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class for metrics collection using Micrometer.
 * Sets up the necessary beans for timing method executions with @Timed annotations.
 */
@Configuration
public class MetricsConfig {

    /**
     * Creates a TimedAspect bean to enable the @Timed annotation.
     * This aspect intercepts methods annotated with @Timed and records their execution time.
     * 
     * @param registry The MeterRegistry to register metrics with
     * @return A configured TimedAspect
     */
    @Bean
    public TimedAspect timedAspect(MeterRegistry registry) {
        return new TimedAspect(registry);
    }
} 