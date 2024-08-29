package com.kaziabid.learn.wams.solr.common.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * @author Kazi Abid
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public record WikipediaSolrQueryResponseData(
        long numFound, long start, double maxScore, boolean numFoundExact,
        List<WikipediaSolrPage> docs) {
}
