package com.kaziabid.learn.wams.solr.client.service.impl;

import java.io.IOException;
import java.util.List;

import org.apache.solr.client.solrj.SolrServerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.kaziabid.learn.wams.common.dto.Page;
import com.kaziabid.learn.wams.common.dto.PagedResponse;
import com.kaziabid.learn.wams.common.dto.solr.WikipediaPageDto;
import com.kaziabid.learn.wams.exceptions.solr.WamsSolrQueryException;
import com.kaziabid.learn.wams.solr.client.model.SolrQueryResponse;
import com.kaziabid.learn.wams.solr.client.repository.SolrQueryClientRepository;
import com.kaziabid.learn.wams.solr.client.service.SolrQueryClientService;
import com.kaziabid.learn.wams.solr.client.utils.SolrDTOUtils;
import com.kaziabid.learn.wams.solr.common.entity.WikipediaSolrPage;

/**
 * @author Kazi Abid
 */
@Service
public class DefaultSolrQueryClientService implements SolrQueryClientService {

    private static final Logger  LOGGER =
            LoggerFactory.getLogger(DefaultSolrQueryClientService.class);
    private final SolrQueryClientRepository solrQueryClientRepository;
    private final SolrDTOUtils   solrDTOUtils;

    public DefaultSolrQueryClientService(SolrQueryClientRepository solrQueryClientRepository, SolrDTOUtils solrDTOUtils) {
        this.solrQueryClientRepository = solrQueryClientRepository;
        this.solrDTOUtils = solrDTOUtils;
    }

    @Override
    public PagedResponse<WikipediaPageDto> query(String query)
            throws WamsSolrQueryException {
        return query(query, Page.build());
    }

    @Override
    public PagedResponse<WikipediaPageDto> query(String query, Page page)
            throws WamsSolrQueryException {
        try {
            SolrQueryResponse<WikipediaSolrPage> solrResponse =
                    solrQueryClientRepository.getWikiPages(query, page);
            List<WikipediaSolrPage> pages = solrResponse.docs();
            LOGGER.debug("Solr query returned results: {}", !pages.isEmpty());
            List<WikipediaPageDto> documents =
                    solrDTOUtils.convertWikiSolrPagesToDTOs(pages);
            LOGGER.debug("Solr query result count: {}", solrResponse.numFound());
            // construct page
            long totalItems = solrResponse.numFound();
            int itemsPerPage = page.itemsPerPage();
            // total pages:
            long remainder = totalItems % itemsPerPage;
            long totalPages = remainder == 0
                    ? totalItems / itemsPerPage : (totalItems / itemsPerPage) + 1;
            Page queryPage = new Page(page.pageNo(), totalPages, itemsPerPage);
            return new PagedResponse<>(queryPage, documents);
        } catch (SolrServerException | IOException e) {
            throw new WamsSolrQueryException(String
                    .format("Unable to query solr server: with query %s and page %s",
                            query, page),
                    e);
        }
    }

}
