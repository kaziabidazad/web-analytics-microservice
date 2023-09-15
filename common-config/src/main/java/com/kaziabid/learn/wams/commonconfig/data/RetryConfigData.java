package com.kaziabid.learn.wams.commonconfig.data;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author Kazi
 */
@Configuration
@ConfigurationProperties(prefix = "retry-config")
public class RetryConfigData {

    private Long initialIntervalMs;
    private Long maxIntervalMs;
    private Double multiplier;
    private Integer maxAttempts;
    private Long sleepTimeMs;

    public Long getInitialIntervalMs() {
        return initialIntervalMs;
    }

    public Long getMaxIntervalMs() {
        return maxIntervalMs;
    }

    public Double getMultiplier() {
        return multiplier;
    }

    public Integer getMaxAttempts() {
        return maxAttempts;
    }

    public Long getSleepTimeMs() {
        return sleepTimeMs;
    }

    public void setInitialIntervalMs(Long initialIntervalMs) {
        this.initialIntervalMs = initialIntervalMs;
    }

    public void setMaxIntervalMs(Long maxIntervalMs) {
        this.maxIntervalMs = maxIntervalMs;
    }

    public void setMultiplier(Double multiplier) {
        this.multiplier = multiplier;
    }

    public void setMaxAttempts(Integer maxAttempts) {
        this.maxAttempts = maxAttempts;
    }

    public void setSleepTimeMs(Long sleepTimeMs) {
        this.sleepTimeMs = sleepTimeMs;
    }

}
