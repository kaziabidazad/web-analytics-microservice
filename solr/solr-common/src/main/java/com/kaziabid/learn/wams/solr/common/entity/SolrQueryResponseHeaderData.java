package com.kaziabid.learn.wams.solr.common.entity;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * @author Kazi Abid
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public record SolrQueryResponseHeaderData(
        boolean zkConnected, int status, @JsonAlias("QTime") long qTime) {
}
