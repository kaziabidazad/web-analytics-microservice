package com.kaziabid.learn.wams.w2k.config;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

import org.springframework.cloud.config.client.RetryProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.backoff.ExponentialBackOffPolicy;
import org.springframework.retry.interceptor.RetryInterceptorBuilder;
import org.springframework.retry.interceptor.RetryOperationsInterceptor;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * @author Kazi
 */
@Configuration
@EnableAsync
public class WikiToKafkaConfig {

    @Bean
    public ExecutorService taskExecutor() {
        ExecutorService threadPoolExecutor = (ThreadPoolExecutor) Executors
                .newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        return threadPoolExecutor;
    }

    @Bean(name = "configServerRetryInterceptor")
    public RetryOperationsInterceptor configServerRetryInterceptor(RetryProperties properties) {
        ExponentialBackOffPolicy backOffPolicy = new ExponentialBackOffPolicy();
        backOffPolicy.setInitialInterval(properties.getInitialInterval());
        backOffPolicy.setMaxInterval(properties.getMaxInterval());
        backOffPolicy.setMultiplier(properties.getMultiplier());
        return RetryInterceptorBuilder.stateless().backOffPolicy(backOffPolicy).maxAttempts(properties.getMaxAttempts())
                .build();
    }
}
