package com.kaziabid.learn.wams.kafka.consumer.config;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.apache.avro.specific.SpecificRecordBase;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;

import com.kaziabid.learn.wams.commonconfig.data.KafkaWikipediaConfigData;
import com.kaziabid.learn.wams.commonconfig.data.KafkaWikipediaConsumerSolrConfigData;

/**
 * @author Kazi
 */
@Configuration
@EnableKafka
@ConditionalOnProperty(prefix = "kafka-consumer-solr-config", name = "consumer-group-id", havingValue = "")
public class KafkaSolrConsumerConfig<K extends Serializable, V extends SpecificRecordBase> {

    private final KafkaWikipediaConfigData kafkaWikipediaConfigData;
    private final KafkaWikipediaConsumerSolrConfigData kafkaWikipediaConsumerSolrConfigData;

    /**
     * @param kafkaWikipediaConfigData
     * @param kafkaWikipediaConsumerSolrConfigData
     */
    public KafkaSolrConsumerConfig(KafkaWikipediaConfigData kafkaWikipediaConfigData,
            KafkaWikipediaConsumerSolrConfigData consumerConfigData) {
        super();
        this.kafkaWikipediaConfigData = kafkaWikipediaConfigData;
        this.kafkaWikipediaConsumerSolrConfigData = consumerConfigData;
    }

    @Bean
    public Map<String, Object> consumerConfig() {
        Map<String, Object> properties = new HashMap<>();
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaWikipediaConfigData.bootstrapServers());
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,
                kafkaWikipediaConsumerSolrConfigData.keyDeserializer());
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,
                kafkaWikipediaConsumerSolrConfigData.valueDeserializer());
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, kafkaWikipediaConsumerSolrConfigData.consumerGroupId());
        properties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, kafkaWikipediaConsumerSolrConfigData.autoOffsetReset());
        properties.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG,
                kafkaWikipediaConsumerSolrConfigData.sessionTimeoutMs());
        properties.put(kafkaWikipediaConfigData.schemaRegistryUrlKey(), kafkaWikipediaConfigData.schemaRegistryUrl());
        properties.put(kafkaWikipediaConsumerSolrConfigData.specificAvroReaderKey(),
                kafkaWikipediaConsumerSolrConfigData.specificAvroReader());
        properties.put(ConsumerConfig.HEARTBEAT_INTERVAL_MS_CONFIG,
                kafkaWikipediaConsumerSolrConfigData.heartbeatIntervalMs());
        properties.put(ConsumerConfig.MAX_POLL_INTERVAL_MS_CONFIG,
                kafkaWikipediaConsumerSolrConfigData.maxPollIntervalMs());
        properties.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, kafkaWikipediaConsumerSolrConfigData.maxPollRecords());
        properties.put(ConsumerConfig.MAX_PARTITION_FETCH_BYTES_CONFIG,
                kafkaWikipediaConsumerSolrConfigData.maxPartitionFetchBytesDefault()
                        * kafkaWikipediaConsumerSolrConfigData.maxPartitionFetchBytesBoostFactor());

        return properties;
    }

    @Bean
    public ConsumerFactory<K, V> consumerFactory() {
        return new DefaultKafkaConsumerFactory<>(consumerConfig());
    }

    @Bean
    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<K, V>> containerFactory() {
        ConcurrentKafkaListenerContainerFactory<K, V> containerFactory = new ConcurrentKafkaListenerContainerFactory<>();
        containerFactory.setConsumerFactory(consumerFactory());
        containerFactory.setBatchListener(kafkaWikipediaConsumerSolrConfigData.batchListener());
        containerFactory.setConcurrency(kafkaWikipediaConsumerSolrConfigData.concurrency());
        containerFactory.setAutoStartup(kafkaWikipediaConsumerSolrConfigData.autoStartup());
        containerFactory.getContainerProperties().setPollTimeout(kafkaWikipediaConsumerSolrConfigData.pollTimeoutMs());
        return containerFactory;
    }

}
