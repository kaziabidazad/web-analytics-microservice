package com.kaziabid.learn.wams.solr.query.service.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

/**
 * @author Kazi Abid
 */
@SpringBootApplication(scanBasePackages = "com.kaziabid.learn.wams")
@ConfigurationPropertiesScan(basePackages = "com.kaziabid.learn.wams")
public class SolrQueryServiceApp {
    public static void main(String[] args) {
        SpringApplication.run(SolrQueryServiceApp.class, args);
    }
}
