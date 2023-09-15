package com.kaziabid.learn.wams.kafkaadmin.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * @author Kazi
 */
@Configuration
public class WebClientConfig {

    @Bean
    public WebClient webClient() {
        WebClient webClient = WebClient.builder().build();
        return webClient;
    }
}
