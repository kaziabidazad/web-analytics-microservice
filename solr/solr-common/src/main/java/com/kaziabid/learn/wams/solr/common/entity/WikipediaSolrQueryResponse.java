package com.kaziabid.learn.wams.solr.common.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * @author Kazi Abid
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public record WikipediaSolrQueryResponse(
        SolrQueryResponseHeaderData responseHeader,
        WikipediaSolrQueryResponseData response) {

}
