package com.kaziabid.learn.wams.solr.indexer.service;

import java.util.List;

import com.kaziabid.learn.wams.common.dto.solr.WikipediaPageDto;
import com.kaziabid.learn.wams.exceptions.solr.WamsSolrIndexerException;

/**
 * @author Kazi Abid
 */
public interface SolrIndexerClientService {

    /**
     * 
     * @param document
     * 
     * @return
     * 
     * @throws WamsSolrIndexerException
     * @throws NullPointerException
     */
    public boolean insert(WikipediaPageDto document)
            throws WamsSolrIndexerException, NullPointerException;

    /**
     * 
     * @param documents
     * 
     * @return
     * 
     * @throws WamsSolrIndexerException
     */
    public boolean insert(List<WikipediaPageDto> document)
            throws WamsSolrIndexerException;

    /**
     * Updates the document. ID is required for the update. If id is not present/or
     * does not match then it will insert the document
     * 
     * @param document
     * 
     * @return
     * 
     * @throws WamsSolrIndexerException
     */
    public boolean update(WikipediaPageDto document) throws WamsSolrIndexerException;

    /**
     * 
     * @param documentId
     * 
     * @return
     * 
     * @throws WamsSolrIndexerException
     */
    public boolean delete(String documentId) throws WamsSolrIndexerException;

}
