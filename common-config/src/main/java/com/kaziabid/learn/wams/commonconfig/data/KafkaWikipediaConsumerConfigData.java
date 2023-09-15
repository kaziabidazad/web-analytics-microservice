package com.kaziabid.learn.wams.commonconfig.data;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author Kazi
 */
@ConfigurationProperties(prefix = "kafka-consumer-config")
public record KafkaWikipediaConsumerConfigData(

        String keyDeserializer,

        String valueDeserializer,

        String consumerGroupId,

        String autoOffsetReset,

        String specificAvroReaderKey,

        String specificAvroReader,

        Boolean batchListener,

        Boolean autoStartup,

        Integer concurrency,

        Integer sessionTimeoutMs,

        Integer heartbeatIntervalMs,

        Integer maxPollIntervalMs,

        Integer maxPollRecords,

        Integer maxPartitionFetchBytesDefault,

        Integer maxPartitionFetchBytesBoostFactor,

        Long pollTimeoutMs

) {

}
