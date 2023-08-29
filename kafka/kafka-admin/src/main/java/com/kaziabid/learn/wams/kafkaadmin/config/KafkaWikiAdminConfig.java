package com.kaziabid.learn.wams.kafkaadmin.config;

import java.util.Map;

import org.apache.kafka.clients.CommonClientConfigs;
import org.apache.kafka.clients.admin.AdminClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.annotation.EnableRetry;

import com.kaziabid.learn.wams.commonconfig.data.KafkaWikipediaConfigData;

/**
 * @author Kazi
 */
@Configuration
@EnableRetry
public class KafkaWikiAdminConfig {

    private KafkaWikipediaConfigData kafkaWikipediaConfigData;

    /**
     * @param kafkaWikipediaConfigData
     */
    public KafkaWikiAdminConfig(KafkaWikipediaConfigData kafkaWikipediaConfigData) {
        super();
        this.kafkaWikipediaConfigData = kafkaWikipediaConfigData;
    }

    @Bean
    public AdminClient adminClient() {
        return AdminClient.create(
                Map.of(CommonClientConfigs.BOOTSTRAP_SERVERS_CONFIG, kafkaWikipediaConfigData.bootstrapServers()));

    }

}
