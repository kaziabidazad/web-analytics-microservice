package com.kaziabid.learn.wams.solr.client.repository.impl;

import java.io.IOException;
import java.util.List;

import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.common.params.SolrParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClient.ResponseSpec;

import com.kaziabid.learn.wams.common.dto.Page;
import com.kaziabid.learn.wams.commonconfig.data.SolrConfigData;
import com.kaziabid.learn.wams.solr.client.model.SolrQueryResponse;
import com.kaziabid.learn.wams.solr.client.repository.SolrQueryClientRepository;
import com.kaziabid.learn.wams.solr.client.utils.SolrQueryHelper;
import com.kaziabid.learn.wams.solr.common.entity.SolrQueryResponseHeaderData;
import com.kaziabid.learn.wams.solr.common.entity.WikipediaSolrPage;
import com.kaziabid.learn.wams.solr.common.entity.WikipediaSolrQueryResponse;
import com.kaziabid.learn.wams.solr.common.entity.WikipediaSolrQueryResponseData;

/**
 * @author Kazi Abid
 */
@Component
public class RestSolrQueryClientRepository implements SolrQueryClientRepository {
    private static final Logger   LOGGER =
            LoggerFactory.getLogger(RestSolrQueryClientRepository.class);
    private final WebClient       solrClient;
    private final SolrQueryHelper solrQueryHelper;
    private final SolrConfigData  solrConfigData;

    public RestSolrQueryClientRepository(WebClient solrClient, SolrQueryHelper solrQueryHelper,
            SolrConfigData solrConfigData) {
        super();
        this.solrClient = solrClient;
        this.solrQueryHelper = solrQueryHelper;
        this.solrConfigData = solrConfigData;
    }

    @Override
    public SolrQueryResponse<WikipediaSolrPage> getWikiPages(String query, Page page)
            throws SolrServerException, IOException {
        SolrParams solrQueryParams = solrQueryHelper.constructSolrQuery(query, page);
        MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
        solrQueryParams
                .forEach(entry -> queryParams.add(entry.getKey(), entry.getValue()[0]));
        LOGGER.info("Querying solr with query {}", solrQueryParams.jsonStr());
        var uriSpec = solrClient.get();
        uriSpec
                .uri(builder -> builder
                        .pathSegment(solrConfigData.wikipediaCollectionName(), "select")
                        .queryParams(queryParams).build(""));

        ResponseSpec responseSpec = uriSpec.retrieve();
        WikipediaSolrQueryResponse response =
                responseSpec.bodyToMono(WikipediaSolrQueryResponse.class).block();
        SolrQueryResponseHeaderData header = response.responseHeader();
        WikipediaSolrQueryResponseData docs = response.response();
        LOGGER.info("Solr query done. Headers: {}", header);
        long totalDocsFound = docs.numFound();
        boolean foundExactMatch = false;// docs.getNumFoundExact();
        double score = docs.maxScore();
        long start = docs.start();
        List<WikipediaSolrPage> documents = docs.docs();
        return new SolrQueryResponse<>(totalDocsFound, start, score, foundExactMatch,
                documents);
    }

}
