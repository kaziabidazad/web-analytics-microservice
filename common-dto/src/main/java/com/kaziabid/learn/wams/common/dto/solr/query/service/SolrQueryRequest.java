package com.kaziabid.learn.wams.common.dto.solr.query.service;

import com.kaziabid.learn.wams.common.dto.Page;

/**
 * @author Kazi Abid
 */
public record SolrQueryRequest(String query, Page page) {

}
