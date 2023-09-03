package com.kaziabid.learn.wams.configserver.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

/**
 * @author Kazi
 */
@SpringBootApplication
@EnableConfigServer
public class ConfigServerApp {

    /**
     * @param args
     */
    public static void main(String[] args) {
        SpringApplication.run(ConfigServerApp.class, args);
    }

}
