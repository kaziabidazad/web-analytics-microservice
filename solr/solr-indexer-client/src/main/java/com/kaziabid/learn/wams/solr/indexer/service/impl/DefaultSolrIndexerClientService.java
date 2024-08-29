package com.kaziabid.learn.wams.solr.indexer.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.kaziabid.learn.wams.common.dto.solr.WikipediaPageDto;
import com.kaziabid.learn.wams.exceptions.solr.WamsSolrIndexerException;
import com.kaziabid.learn.wams.solr.client.utils.SolrDTOUtils;
import com.kaziabid.learn.wams.solr.indexer.repository.SolrIndexerClientRepository;
import com.kaziabid.learn.wams.solr.indexer.service.SolrIndexerClientService;

/**
 * @author Kazi Abid
 */
@Service
public class DefaultSolrIndexerClientService implements SolrIndexerClientService {
    private final SolrIndexerClientRepository indexerRepository;
    private final SolrDTOUtils                solrDTOUtils;

    public DefaultSolrIndexerClientService(SolrIndexerClientRepository indexerRepository,
            SolrDTOUtils solrDTOUtils) {
        super();
        this.indexerRepository = indexerRepository;
        this.solrDTOUtils = solrDTOUtils;
    }

    @Override
    public boolean insert(WikipediaPageDto document)
            throws WamsSolrIndexerException, NullPointerException {
        if (document == null)
            throw new NullPointerException("Document is null.");
        if (document.id() != null)
            return update(document);
        return indexerRepository
                .insert(solrDTOUtils.convertDtoToWikipediaSolrPage(document));
    }

    @Override
    public boolean insert(List<WikipediaPageDto> documents)
            throws WamsSolrIndexerException {
        if (documents == null)
            throw new NullPointerException("Document is null.");
        return indexerRepository
                .insert(solrDTOUtils.convertDtoToWikipediaSolrPage(documents));
    }

    @Override
    public boolean update(WikipediaPageDto document) throws WamsSolrIndexerException {
        if (document == null)
            throw new NullPointerException("Document is null.");
        if (document.id() == null)
            return insert(document);
        return indexerRepository
                .update(solrDTOUtils.convertDtoToWikipediaSolrPage(document));
    }

    @Override
    public boolean delete(String documentId) throws WamsSolrIndexerException {
        return indexerRepository.delete(documentId);
    }

}
