package com.kaziabid.learn.wams.k2s.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.kaziabid.learn.wams.k2s.listener.QueueListener;

/**
 * @author Kazi
 */
@Component
public class KafkaToSolrStartupRunner implements ApplicationRunner {

    private final static Logger LOGGER = LoggerFactory
            .getLogger(KafkaToSolrStartupRunner.class);
    private final QueueListener queueListener;

    /**
     * @param queueListener
     */
    public KafkaToSolrStartupRunner(QueueListener queueListener) {
        super();
        this.queueListener = queueListener;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        String appStartedBanner = """

                ***************************************************************************
                ***************************************************************************
                ***                                                                     ***
                ***                                                                     ***
                ***                                                                     ***
                ***                     Kafka to Solr Service Started!                  ***
                ***                                                                     ***
                ***                                                                     ***
                ***                                                                     ***
                ***************************************************************************
                ***************************************************************************
                                        """;
        LOGGER.info(appStartedBanner);
        queueListener.startListening();
    }

}
