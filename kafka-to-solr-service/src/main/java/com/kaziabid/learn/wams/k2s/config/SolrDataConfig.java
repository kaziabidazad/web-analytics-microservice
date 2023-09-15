package com.kaziabid.learn.wams.k2s.config;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.impl.CloudSolrClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.kaziabid.learn.wams.commonconfig.data.KafkaToSolrConfigData;

/**
 * @author Kazi
 */
@Configuration
public class SolrDataConfig {

    private final KafkaToSolrConfigData kafkaToSolrConfigData;

    /**
     * @param kafkaToSolrConfigData
     */
    public SolrDataConfig(KafkaToSolrConfigData kafkaToSolrConfigData) {
        super();
        this.kafkaToSolrConfigData = kafkaToSolrConfigData;
    }

    @Bean
    public SolrClient solrClient() {
        CloudSolrClient solrClient = new CloudSolrClient.Builder(kafkaToSolrConfigData.solrUrls())
                .withDefaultCollection(
                        kafkaToSolrConfigData.wikipediaCollectionName())
                .build();
        return solrClient;
    }

}
