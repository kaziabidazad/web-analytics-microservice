package com.kaziabid.learn.wams.commonconfig.data;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author Kazi Abid
 */
@ConfigurationProperties(prefix = "solr-query-web-client-service")
public record SolrQueryWebClientServiceWebClientConfigData(
        WebClientConfigData webClient) {

    public record WebClientConfigData(
            long connectTimeoutMs, long readTimeoutMs, long writeTimeoutMs,
            int maxInMemorySize, String contentType, String acceptType,
            String solrQueryServiceBaseUrl, String serviceId,
            List<SolrQueryServiceInstance> instances) {

    }

    public record SolrQueryServiceInstance(String id, String host, int port) {

    }

}
