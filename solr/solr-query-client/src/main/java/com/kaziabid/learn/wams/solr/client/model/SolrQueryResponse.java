package com.kaziabid.learn.wams.solr.client.model;

import java.util.List;

/**
 * @author Kazi Abid
 */
public record SolrQueryResponse<T>(
        long numFound, long start, double maxScore, boolean numFoundExact, List<T> docs) {

}
