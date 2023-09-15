package com.kaziabid.learn.wams.kafka.producer.config;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.apache.avro.specific.SpecificRecordBase;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import com.kaziabid.learn.wams.commonconfig.data.KafkaWikipediaConfigData;
import com.kaziabid.learn.wams.commonconfig.data.KafkaWikipediaProducerConfigData;

/**
 * @author Kazi
 */
@Configuration
public class KafkaProducerCofig<K extends Serializable, V extends SpecificRecordBase> {

    private final KafkaWikipediaProducerConfigData kafkaWikipediaProducerConfigData;

    private final KafkaWikipediaConfigData kafkaWikipediaConfigData;

    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaProducerCofig.class);

    /**
     * @param kafkaWikipediaProducerConfigData
     * @param kafkaWikipediaConfigData
     */
    public KafkaProducerCofig(KafkaWikipediaProducerConfigData kafkaWikipediaProducerConfigData,
            KafkaWikipediaConfigData kafkaWikipediaConfigData) {
        super();
        this.kafkaWikipediaProducerConfigData = kafkaWikipediaProducerConfigData;
        this.kafkaWikipediaConfigData = kafkaWikipediaConfigData;
    }

    @Bean
    public Map<String, Object> producerConfig() {
        Map<String, Object> properties = new HashMap<>();
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaWikipediaConfigData.bootstrapServers());
        properties.put(kafkaWikipediaConfigData.schemaRegistryUrlKey(), kafkaWikipediaConfigData.schemaRegistryUrl());
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
                kafkaWikipediaProducerConfigData.keySerializerClass());
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
                kafkaWikipediaProducerConfigData.valueSerializerClass());
        properties.put(ProducerConfig.LINGER_MS_CONFIG, kafkaWikipediaProducerConfigData.lingerMs());
        properties.put(ProducerConfig.COMPRESSION_TYPE_CONFIG, kafkaWikipediaProducerConfigData.compressionType());
        properties.put(ProducerConfig.ACKS_CONFIG, kafkaWikipediaProducerConfigData.acks());
        properties.put(ProducerConfig.REQUEST_TIMEOUT_MS_CONFIG, kafkaWikipediaProducerConfigData.requestTimeoutMs());
        properties.put(ProducerConfig.RETRIES_CONFIG, kafkaWikipediaProducerConfigData.retryCount());
        LOGGER.debug("kafka Producer Config::");
        properties.forEach((k, v) -> {
            LOGGER.debug("{} : {} ", k, v);
        });
        return properties;
    }

    @Bean
    public ProducerFactory<K, V> producerFactory() {
        ProducerFactory<K, V> producerFactory = new DefaultKafkaProducerFactory<>(producerConfig());
        return producerFactory;
    }

    @Bean
    public KafkaTemplate<K, V> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }
}
