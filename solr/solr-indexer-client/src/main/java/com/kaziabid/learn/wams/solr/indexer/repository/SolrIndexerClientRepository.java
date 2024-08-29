package com.kaziabid.learn.wams.solr.indexer.repository;

import java.util.List;

import com.kaziabid.learn.wams.exceptions.solr.WamsSolrIndexerException;
import com.kaziabid.learn.wams.exceptions.solr.WamsSolrQueryException;
import com.kaziabid.learn.wams.solr.common.entity.WikipediaSolrPage;

/**
 * @author Kazi Abid
 */
public interface SolrIndexerClientRepository {
    /**
     * Insert a new document
     * 
     * @param document
     * 
     * @return
     * 
     * @throws WamsSolrQueryException
     */
    public boolean insert(WikipediaSolrPage document) throws WamsSolrIndexerException;

    /**
     * 
     * @param documents
     * 
     * @return
     * 
     * @throws WamsSolrQueryException
     */
    public boolean insert(List<WikipediaSolrPage> documents)
            throws WamsSolrIndexerException;

    /**
     * Updates the document. ID is required for the update.
     * 
     * @param document
     * 
     * @return
     * 
     * @throws WamsSolrQueryException
     * @throws IllegalArgumentException - If ID is not present
     */
    public boolean update(WikipediaSolrPage document)
            throws WamsSolrIndexerException, IllegalArgumentException;

    /**
     * 
     * @param documentId
     * 
     * @return
     * 
     * @throws WamsSolrQueryException
     */
    public boolean delete(String documentId) throws WamsSolrIndexerException;
}
