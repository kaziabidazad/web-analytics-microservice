package com.kaziabid.learn.wams.solr.client.service;

import com.kaziabid.learn.wams.common.dto.Page;
import com.kaziabid.learn.wams.common.dto.PagedResponse;
import com.kaziabid.learn.wams.common.dto.solr.WikipediaPageDto;
import com.kaziabid.learn.wams.exceptions.solr.WamsSolrQueryException;

/**
 * @author Kazi Abid
 */
public interface SolrQueryClientService {

    public PagedResponse<WikipediaPageDto> query(String query) throws WamsSolrQueryException;

    public PagedResponse<WikipediaPageDto> query(String query, Page page)
            throws WamsSolrQueryException;
}
