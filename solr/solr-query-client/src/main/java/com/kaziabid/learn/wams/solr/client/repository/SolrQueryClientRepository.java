package com.kaziabid.learn.wams.solr.client.repository;

import java.io.IOException;

import org.apache.solr.client.solrj.SolrServerException;

import com.kaziabid.learn.wams.common.dto.Page;
import com.kaziabid.learn.wams.solr.client.model.SolrQueryResponse;
import com.kaziabid.learn.wams.solr.common.entity.WikipediaSolrPage;

/**
 * @author Kazi Abid
 */
public interface SolrQueryClientRepository {

    /**
     * Default qeury with score order desc
     * 
     * @param query
     * @param page
     * 
     * @return
     * 
     * @throws IOException
     * @throws SolrServerException
     */
    public SolrQueryResponse<WikipediaSolrPage> getWikiPages(String query, Page page)
            throws SolrServerException, IOException;

}
