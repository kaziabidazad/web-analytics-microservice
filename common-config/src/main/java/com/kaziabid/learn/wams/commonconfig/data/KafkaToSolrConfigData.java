package com.kaziabid.learn.wams.commonconfig.data;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author Kazi
 */
@ConfigurationProperties(prefix = "solr")
public record KafkaToSolrConfigData(
        String wikipediaCollectionName,
        List<String> zookeeperUrls,
        List<String> solrUrls) {

}
