package com.kaziabid.learn.wams.w2k.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

/**
 * @author Kazi
 */
@SpringBootApplication(scanBasePackages = "com.kaziabid.learn.wams")
@ConfigurationPropertiesScan(basePackages = "com.kaziabid.learn.wams")
public class WikipediaToKafkaServiceApp {

    public static void main(String[] args) {
        SpringApplication.run(WikipediaToKafkaServiceApp.class, args);
    }

}
