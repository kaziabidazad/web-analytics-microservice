package com.kaziabid.learn.wams.kafka.consumer.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;

import com.kaziabid.learn.wams.commonconfig.data.KafkaWikipediaConfigData;
import com.kaziabid.learn.wams.commonconfig.data.KafkaWikipediaConsumerSolrConfigData;

/**
 * 
 * @author Kazi Abid
 */
@EnableKafka
@Configuration
public class KafkaSolrConsumerConfig {

    private static final Logger                        LOGGER =
            LoggerFactory.getLogger(KafkaSolrConsumerConfig.class);
    private final KafkaWikipediaConsumerSolrConfigData consumerSolrConfigData;
    private final KafkaWikipediaConfigData             kafkaWikipediaConfigData;

    public KafkaSolrConsumerConfig(
            KafkaWikipediaConsumerSolrConfigData consumerSolrConfigData,
            KafkaWikipediaConfigData kafkaWikipediaConfigData) {
        super();
        this.consumerSolrConfigData = consumerSolrConfigData;
        this.kafkaWikipediaConfigData = kafkaWikipediaConfigData;
    }

    @Bean
    public Map<String, Object> consumerConfig() {
        Map<String, Object> properties = new HashMap<>();
        properties
                .put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,
                        kafkaWikipediaConfigData.bootstrapServers());
        properties
                .put(kafkaWikipediaConfigData.schemaRegistryUrlKey(),
                        kafkaWikipediaConfigData.schemaRegistryUrl());
        properties
                .put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,
                        consumerSolrConfigData.keyDeserializer());
        properties
                .put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,
                        consumerSolrConfigData.valueDeserializer());
        properties
                .put(consumerSolrConfigData.specificAvroReaderKey(),
                        consumerSolrConfigData.specificAvroReader());
        properties
                .put(ConsumerConfig.REQUEST_TIMEOUT_MS_CONFIG,
                        consumerSolrConfigData.pollTimeoutMs());
        properties
                .put(ConsumerConfig.MAX_POLL_INTERVAL_MS_CONFIG,
                        consumerSolrConfigData.maxPollIntervalMs());
        properties
                .put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG,
                        consumerSolrConfigData.maxPollRecords());
        properties
                .put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG,
                        consumerSolrConfigData.autoOffsetReset());
        LOGGER.debug("kafka Producer Config::");
        properties.forEach((k, v) -> LOGGER.debug("{} : {} ", k, v));
        return properties;
    }

    @Bean
    public ConsumerFactory<String, String> kafkaConsumerFactory() {
        return new DefaultKafkaConsumerFactory<>(consumerConfig());
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, String>
            kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, String> containerFactory =
                new ConcurrentKafkaListenerContainerFactory<>();
        containerFactory.setConsumerFactory(kafkaConsumerFactory());
        return containerFactory;
    }
}