package com.kaziabid.learn.wams.commonconfig.data;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author Kazi Abid
 */
@ConfigurationProperties(prefix = "solr-query-service")
public record SolrQueryWebClientServiceSolrQueryServiceConfigData(
        SolrServiceQueryConfifData query) {

    public record SolrServiceQueryConfifData(SolrServiceQueryParam params) {

    }

    public record SolrServiceQueryParam(
            String queryParam, String pageNumberParam, String itemsPerPageParam) {

    }

}
