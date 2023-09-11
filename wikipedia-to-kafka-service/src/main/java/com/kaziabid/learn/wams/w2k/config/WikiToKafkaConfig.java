package com.kaziabid.learn.wams.w2k.config;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
}
