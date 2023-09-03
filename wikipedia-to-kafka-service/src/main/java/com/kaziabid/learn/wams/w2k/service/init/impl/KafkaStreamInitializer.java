package com.kaziabid.learn.wams.w2k.service.init.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.kaziabid.learn.wams.commonconfig.data.KafkaWikipediaConfigData;
import com.kaziabid.learn.wams.kafkaadmin.clients.KafkaWikipediaAdminClient;
import com.kaziabid.learn.wams.w2k.service.init.StreamInitializer;

/**
 * @author Kazi
 */
@Component
public class KafkaStreamInitializer implements StreamInitializer {

    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaStreamInitializer.class);

    private final KafkaWikipediaConfigData kafkaWikipediaConfigData;

    private final KafkaWikipediaAdminClient kafkaWikipediaAdminClient;

    /**
     * @param kafkaWikipediaConfigData
     * @param kafkaWikipediaAdminClient
     */
    public KafkaStreamInitializer(KafkaWikipediaConfigData kafkaWikipediaConfigData,
            KafkaWikipediaAdminClient kafkaWikipediaAdminClient) {
        super();
        this.kafkaWikipediaConfigData = kafkaWikipediaConfigData;
        this.kafkaWikipediaAdminClient = kafkaWikipediaAdminClient;
    }

    @Override
    public void init() {
        kafkaWikipediaAdminClient.createTopics();
        kafkaWikipediaAdminClient.checkSchemaRegistry();
        LOGGER.info("Topics with name {} is ready for operations!",
                kafkaWikipediaConfigData.topicNamesToCreate().toArray());

    }

}
