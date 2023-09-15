package com.kaziabid.learn.wams.k2s.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

/**
 * @author Kazi
 */
@SpringBootApplication(scanBasePackages = "com.kaziabid.learn.wams")
@ConfigurationPropertiesScan(basePackages = "com.kaziabid.learn.wams")
public class KafkaToSolrServiceApp {

    /**
     * @param args
     */
    public static void main(String[] args) {
        SpringApplication.run(KafkaToSolrServiceApp.class, args);
        
        
    }

}
