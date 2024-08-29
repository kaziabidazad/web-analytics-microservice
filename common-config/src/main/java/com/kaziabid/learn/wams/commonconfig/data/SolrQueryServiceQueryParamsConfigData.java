package com.kaziabid.learn.wams.commonconfig.data;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author Kazi Abid
 */
@ConfigurationProperties(prefix = "solr-query-service.query.params")
public record SolrQueryServiceQueryParamsConfigData(
        String pageNumberParam, String itemsPerPageParam) {
}
