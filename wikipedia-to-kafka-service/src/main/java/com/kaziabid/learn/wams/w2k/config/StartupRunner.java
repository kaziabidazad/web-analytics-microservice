package com.kaziabid.learn.wams.w2k.config;

import java.time.LocalDate;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.kaziabid.learn.wams.commonconfig.data.KafkaWikipediaProducerConfigData;
import com.kaziabid.learn.wams.w2k.service.WikipediaFeedRunner;

/**
 * @author Kazi
 */
@Component
public class StartupRunner implements ApplicationRunner {

    private static final Logger LOGGER = LoggerFactory.getLogger(StartupRunner.class);

    private WikipediaFeedRunner wikipediaFeedRunner;
    private KafkaWikipediaProducerConfigData kafkaWikipediaProducerConfigData;

    /**
     * @param wikipediaFeedRunner
     */
    public StartupRunner(WikipediaFeedRunner wikipediaFeedRunner,
            KafkaWikipediaProducerConfigData kafkaWikipediaProducerConfigData) {
        super();
        this.wikipediaFeedRunner = wikipediaFeedRunner;
        this.kafkaWikipediaProducerConfigData = kafkaWikipediaProducerConfigData;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        kafkaWikipediaProducerConfigData.batchSize();
        String appStartedBanner = """

                ***************************************************************************
                ***************************************************************************
                ***                                                                     ***
                ***                                                                     ***
                ***                                                                     ***
                ***                Wikipedia to Kafka Service Started!                  ***
                ***                                                                     ***
                ***                                                                     ***
                ***                                                                     ***
                ***************************************************************************
                ***************************************************************************
                                        """;
        LOGGER.info(appStartedBanner);
        try {
            startFeedRunner();
        } catch (Exception e) {
            LOGGER.error("Error starting Stream Runner: ", e);
        }

    }

    public void startFeedRunner() {
        LOGGER.info("Wikipedia Extraction start date: {} ", kafkaWikipediaProducerConfigData.startDate());
        LocalDate startDate = kafkaWikipediaProducerConfigData.startDate();
        wikipediaFeedRunner.start(startDate);
        String message = """

                *****************************************************************************
                *****************************************************************************
                ******                                                                 ******
                ******                                                                 ******
                ******                     Press Enter to Stop                         ******
                ******                                                                 ******
                ******                                                                 ******
                *****************************************************************************
                *****************************************************************************
                """;
        LOGGER.error(message);
        try (Scanner scanner = new Scanner(System.in)) {
            scanner.nextLine();
            wikipediaFeedRunner.stop();
        }
    }
}
