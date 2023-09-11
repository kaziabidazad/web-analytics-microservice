package com.kaziabid.learn.wams.k2s.consumer.service.impl;

import java.util.Arrays;
import java.util.List;

import org.apache.solr.client.solrj.impl.CloudSolrClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.config.KafkaListenerEndpointRegistry;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import com.kaziabid.learn.wams.commonconfig.data.KafkaWikipediaConfigData;
import com.kaziabid.learn.wams.k2s.consumer.service.KafkaSolrConsumer;
import com.kaziabid.learn.wams.kafka.model.avro.WikipediaPageAvroModel;
import com.kaziabid.learn.wams.kafkaadmin.clients.KafkaWikipediaAdminClient;

/**
 * @author Kazi
 */
@Component
public class WikipediaKafkaToSolrConsumer implements KafkaSolrConsumer<Long, WikipediaPageAvroModel> {

    private static final Logger LOGGER = LoggerFactory.getLogger(WikipediaKafkaToSolrConsumer.class);
    private final KafkaListenerEndpointRegistry kafkaListenerEndpointRegistry;
    private final KafkaWikipediaAdminClient kafkaWikipediaAdminClient;
    private final KafkaWikipediaConfigData kafkaWikipediaConfigData;
    private final String listerID = "wikipediaPagesToSolrTopicListener";

    /**
     * @param kafkaListenerEndpointRegistry
     * @param kafkaWikipediaAdminClient
     * @param kafkaWikipediaConfigData
     */
    public WikipediaKafkaToSolrConsumer(KafkaListenerEndpointRegistry kafkaListenerEndpointRegistry,
            KafkaWikipediaAdminClient kafkaWikipediaAdminClient, KafkaWikipediaConfigData kafkaConfigData) {
        super();
        this.kafkaListenerEndpointRegistry = kafkaListenerEndpointRegistry;
        this.kafkaWikipediaAdminClient = kafkaWikipediaAdminClient;
        this.kafkaWikipediaConfigData = kafkaConfigData;
    }

    @EventListener(value = ApplicationStartedEvent.class)
    public void onStartup() {
        kafkaWikipediaAdminClient.checkTopicsCreated();
        LOGGER.info("Topics with name {} is ready for operation!",
                kafkaWikipediaConfigData.topicNamesToCreate().toArray());
        kafkaListenerEndpointRegistry.getListenerContainer(listerID).start();
    }

    @Override
    @KafkaListener(id = listerID, topics = "${kafka-config.wikipedia.topic-name}")
    public void receive(@Payload List<WikipediaPageAvroModel> messages,
            @Header(KafkaHeaders.RECEIVED_PARTITION) List<Integer> partitions,
            @Header(KafkaHeaders.OFFSET) List<Long> offsets) {

        LOGGER.info(
                "{} number of messages received with partitions {} and offsets {}, "
                        + "sending it to elastic: Thread id: {}",
                messages.size(), partitions.toString(), offsets.toString(), Thread.currentThread().getId());

        CloudSolrClient solrClient = new CloudSolrClient.Builder(Arrays.asList("", ""), null).build();
    }

}
