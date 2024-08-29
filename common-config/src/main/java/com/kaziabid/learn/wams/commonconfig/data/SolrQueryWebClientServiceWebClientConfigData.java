package com.kaziabid.learn.wams.commonconfig.data;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author Kazi Abid
 */
@ConfigurationProperties(prefix = "solr-query-web-client-service")
public record SolrQueryWebClientServiceWebClientConfigData(
        WebClientConfigData webClient) {

    public record WebClientConfigData(
            long connectTimeoutMs, long readTimeoutMs, long writeTimeoutMs,
            long maxInMemorySize, String contentType, String acceptType,
            String solrQueryServiceBaseUrl) {

    }

}
