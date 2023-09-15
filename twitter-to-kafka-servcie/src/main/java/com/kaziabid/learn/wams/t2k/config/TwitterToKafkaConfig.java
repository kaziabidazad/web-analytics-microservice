package com.kaziabid.learn.wams.t2k.config;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

/**
 * @author Kazi
 */
@Configuration
public class TwitterToKafkaConfig {

    @Value("${twitter-to-kafka-service.twitter-filter-keywords}")
    List<String> twitterKeywords;

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertyPlaceholderConfigurer() throws IOException {
        PropertySourcesPlaceholderConfigurer propertyPlaceholderConfigurer = new PropertySourcesPlaceholderConfigurer();
//        propertyPlaceholderConfigurer.setLocations(new ClassPathResource("datasource.properties"));
        propertyPlaceholderConfigurer.setIgnoreResourceNotFound(false);
        propertyPlaceholderConfigurer.setIgnoreUnresolvablePlaceholders(false);
        return propertyPlaceholderConfigurer;
    }

}
