package com.kaziabid.learn.wams.commonconfig.data;

import java.time.LocalDate;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author Kazi
 */
@ConfigurationProperties(prefix = "kafka-producer-config.wikipedia")
public record KafkaWikipediaProducerConfigData(
        String keySerializerClass,
        String valueSerializerClass,
        String compressionType,
        String acks,
        Integer batchSize,
        Integer batchSizeBoostFactor,
        Integer lingerMs,
        Integer requestTimeoutMs,
        Integer retryCount,
        LocalDate startDate,
        Integer maxRequestSize

) {

}
