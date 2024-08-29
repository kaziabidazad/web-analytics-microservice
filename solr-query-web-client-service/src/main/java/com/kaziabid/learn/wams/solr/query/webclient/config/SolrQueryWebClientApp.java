package com.kaziabid.learn.wams.solr.query.webclient.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

/**
 * @author Kazi Abid
 */
@SpringBootApplication(scanBasePackages = "com.kaziabid.learn.wams")
@ConfigurationPropertiesScan(basePackages = "com.kaziabid.learn.wams")
public class SolrQueryWebClientApp {
    public static void main(String[] args) {
        SpringApplication.run(SolrQueryWebClientApp.class, args);
    }

}
