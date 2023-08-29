package com.kaziabid.learn.wams.commonconfig.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.backoff.ExponentialBackOffPolicy;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;

import com.kaziabid.learn.wams.commonconfig.data.RetryConfigData;

/**
 * @author Kazi
 */
@Configuration
public class RetryConfig {

    private RetryConfigData retryConfigData;

    /**
     * @param retryConfigData
     */
    public RetryConfig(RetryConfigData retryConfigData) {
        super();
        this.retryConfigData = retryConfigData;
    }

    @Bean
    public RetryTemplate retryTemplate() {
        RetryTemplate retryTemplate = new RetryTemplate();
        ExponentialBackOffPolicy backOffPolicy = new ExponentialBackOffPolicy();
        backOffPolicy.setInitialInterval(retryConfigData.getInitialIntervalMs());
        backOffPolicy.setMaxInterval(retryConfigData.getMaxIntervalMs());
        backOffPolicy.setMultiplier(retryConfigData.getMultiplier());

        // Retry until max attempts is reached
        SimpleRetryPolicy retryPolicy = new SimpleRetryPolicy();
        retryPolicy.maxAttemptsSupplier(() -> retryConfigData.getMaxAttempts());

        retryTemplate.setBackOffPolicy(backOffPolicy);
        retryTemplate.setRetryPolicy(retryPolicy);

        return retryTemplate;
    }
}
