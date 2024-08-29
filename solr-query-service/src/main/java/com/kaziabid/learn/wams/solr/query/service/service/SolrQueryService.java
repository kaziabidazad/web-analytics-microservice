package com.kaziabid.learn.wams.solr.query.service.service;

import org.springframework.stereotype.Service;

import com.kaziabid.learn.wams.common.dto.Page;
import com.kaziabid.learn.wams.common.dto.PagedResponse;
import com.kaziabid.learn.wams.common.dto.solr.WikipediaPageDto;
import com.kaziabid.learn.wams.exceptions.solr.WamsSolrQueryException;
import com.kaziabid.learn.wams.solr.client.service.SolrQueryClientService;

/**
 * @author Kazi Abid
 */
@Service
public class SolrQueryService {

    private final SolrQueryClientService solrQueryClientService;

    public SolrQueryService(SolrQueryClientService solrQueryClientService) {
        super();
        this.solrQueryClientService = solrQueryClientService;
    }

    public PagedResponse<WikipediaPageDto> query() throws WamsSolrQueryException {
        Page firstPage = Page.build();
        return solrQueryClientService.query("*", firstPage);
    }

    public PagedResponse<WikipediaPageDto> query(String query, int itemsPerPage,
            int pageNumber)
            throws WamsSolrQueryException {
        Page page = new Page(pageNumber, 0, itemsPerPage);
        return solrQueryClientService.query(query, page);
    }
}
