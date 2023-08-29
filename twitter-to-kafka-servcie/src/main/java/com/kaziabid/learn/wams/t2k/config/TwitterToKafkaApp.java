package com.kaziabid.learn.wams.t2k.config;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;

import com.kaziabid.learn.wams.t2k.service.StreamRunner;

/**
 * @author Kazi
 */
@SpringBootApplication(scanBasePackages = "com.kaziabid.learn.wams.t2k")
public class TwitterToKafkaApp {

    private static final Logger LOGGER = LoggerFactory.getLogger(TwitterToKafkaApp.class);

    @Value("${twitter-to-kafka-service.twitter-filter-keywords}")
    List<String> twitterKeywords;

    private StreamRunner streamRunner;

    /**
     * @param streamRunner
     */
    @Autowired
    public TwitterToKafkaApp(StreamRunner streamRunner) {
        super();
        this.streamRunner = streamRunner;
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        SpringApplication.run(TwitterToKafkaApp.class, args);
    }

    @EventListener(value = ContextRefreshedEvent.class)
    public void contextRefreshedEvent() {
        String appStartedBanner = """

                ***************************************************************************
                ***             Twitter to Kafka Service Started!                       ***
                ***************************************************************************
                                        """;
        LOGGER.info(appStartedBanner);
        LOGGER.info("Twitter keywords: " + twitterKeywords);
        try {
            streamRunner.start();
        } catch (Exception e) {
            LOGGER.error("Error starting Stream Runner: ", e);
        }
    }

}
