package com.kaziabid.learn.wams.solr.client.repository.impl;

import java.io.IOException;
import java.util.List;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrRequest.METHOD;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.params.SolrParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kaziabid.learn.wams.common.dto.Page;
import com.kaziabid.learn.wams.solr.client.model.SolrQueryResponse;
import com.kaziabid.learn.wams.solr.client.repository.SolrQueryClientRepository;
import com.kaziabid.learn.wams.solr.client.utils.SolrDTOUtils;
import com.kaziabid.learn.wams.solr.client.utils.SolrQueryHelper;
import com.kaziabid.learn.wams.solr.common.entity.WikipediaSolrPage;

/**
 * @author Kazi Abid
 */
//@Component
public class DefaultSolrQueryClientRepository implements SolrQueryClientRepository {
    private static final Logger LOGGER =
            LoggerFactory.getLogger(DefaultSolrQueryClientRepository.class);

    private final SolrClient      solrClient;
    private final SolrDTOUtils    solrDTOUtils;
    private final SolrQueryHelper solrQueryHelper;

    public DefaultSolrQueryClientRepository(SolrClient solrClient, SolrDTOUtils solrDTOUtils,
            SolrQueryHelper solrQueryHelper) {
        super();
        this.solrClient = solrClient;
        this.solrDTOUtils = solrDTOUtils;
        this.solrQueryHelper = solrQueryHelper;

    }

    @Override
    public SolrQueryResponse<WikipediaSolrPage> getWikiPages(String query, Page page)
            throws SolrServerException, IOException {
        SolrParams solrQueryParams = solrQueryHelper.constructSolrQuery(query, page);
        LOGGER.info("Querying solr with query {}", solrQueryParams.jsonStr());
        QueryResponse response = solrClient.query(solrQueryParams, METHOD.GET);
        SolrDocumentList docs = response.getResults();
        long totalDocsFound = docs.getNumFound();
        boolean foundExactMatch = false;//docs.getNumFoundExact();
        double score = docs.getMaxScore();
        long start = docs.getStart();
        List<WikipediaSolrPage> documents =
                solrDTOUtils.convertSolrDocsToWikiSolrPages(docs);
        return new SolrQueryResponse<>(totalDocsFound, start, score, foundExactMatch,
                documents);
    }

}
