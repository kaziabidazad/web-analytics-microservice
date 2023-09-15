package com.kaziabid.learn.wams.k2e.consumer.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.config.KafkaListenerEndpointRegistry;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import com.kaziabid.learn.wams.commonconfig.data.KafkaWikipediaConfigData;
import com.kaziabid.learn.wams.k2e.consumer.service.KafkaConsumer;
import com.kaziabid.learn.wams.kafka.model.avro.WikipediaPageAvroModel;
import com.kaziabid.learn.wams.kafkaadmin.clients.KafkaWikipediaAdminClient;

/**
 * @author Kazi
 */
@Service
public class WikipediaKafkaConsumer implements KafkaConsumer<Long, WikipediaPageAvroModel> {

    private static final Logger LOGGER = LoggerFactory.getLogger(WikipediaKafkaConsumer.class);

    private final KafkaListenerEndpointRegistry kafkaListenerEndpointRegistry;
    private final KafkaWikipediaAdminClient kafkaWikipediaAdminClient;
    private final KafkaWikipediaConfigData kafkaConfigData;

    /**
     * @param kafkaListenerEndpointRegistry
     * @param kafkaWikipediaAdminClient
     * @param kafkaConfigData
     */
    public WikipediaKafkaConsumer(KafkaListenerEndpointRegistry kafkaListenerEndpointRegistry,
            KafkaWikipediaAdminClient kafkaWikipediaAdminClient, KafkaWikipediaConfigData kafkaConfigData) {
        super();
        this.kafkaListenerEndpointRegistry = kafkaListenerEndpointRegistry;
        this.kafkaWikipediaAdminClient = kafkaWikipediaAdminClient;
        this.kafkaConfigData = kafkaConfigData;
    }

    @EventListener
    public void onStartup(ApplicationStartedEvent applicationStartedEvent) {
        kafkaWikipediaAdminClient.checkTopicsCreated();
        LOGGER.info("Topics with name {} is ready for operation!", kafkaConfigData.topicNamesToCreate().toArray());
        kafkaListenerEndpointRegistry.getListenerContainer("wikipediaPagesTopicListener").start();
    }

    @Override
    @KafkaListener(id = "wikipediaPagesTopicListener", topics = "${kafka-config.wikipedia.topic-name}")
    public void receive(@Payload List<WikipediaPageAvroModel> messages,
            @Header(KafkaHeaders.RECEIVED_PARTITION) List<Integer> partitions,
            @Header(KafkaHeaders.OFFSET) List<Long> offsets) {

        LOGGER.info(
                "{} number of messages received with partitions {} and offsets {}, "
                        + "sending it to elastic: Thread id: {}",
                messages.size(), partitions.toString(), offsets.toString(), Thread.currentThread().getId());

    }

}
