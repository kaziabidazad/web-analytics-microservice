package com.kaziabid.learn.wams.commonconfig.data;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author Kazi
 */
@ConfigurationProperties(prefix = "kafka-config.wikipedia")
public record KafkaWikipediaConfigData(

        String bootstrapServers,

        String schemaRegistryUrlKey,

        String schemaRegistryUrl,

        String topicName,

        List<String> topicsNamesToCreate,

        Integer numberOfpartitions,

        Short replicationFactor) {

}
