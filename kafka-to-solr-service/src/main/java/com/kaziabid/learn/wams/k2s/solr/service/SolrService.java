package com.kaziabid.learn.wams.k2s.solr.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kaziabid.learn.wams.common.dto.solr.WikipediaPageDto;
import com.kaziabid.learn.wams.exceptions.solr.WamsSolrIndexerException;
import com.kaziabid.learn.wams.k2s.converter.EntityConverter;
import com.kaziabid.learn.wams.kafka.model.avro.WikipediaPageAvroModel;
import com.kaziabid.learn.wams.solr.indexer.service.SolrIndexerClientService;

/**
 * @author Kazi
 */
@Service
public class SolrService {
    private static final Logger LOGGER = LoggerFactory.getLogger(SolrService.class);

    @SuppressWarnings("unused")
    private final ObjectMapper             objectMapper;
    private final EntityConverter          entityConverter;
    private final SolrIndexerClientService indexerClientService;

    /**
     * 
     * @param objectMapper
     * @param entityConverter
     */
    public SolrService(
            @Autowired @Qualifier("wikiPageObjectMapper") ObjectMapper objectMapper,
            EntityConverter entityConverter,
            SolrIndexerClientService indexerClientService) {
        super();
        this.objectMapper = objectMapper;
        this.entityConverter = entityConverter;
        this.indexerClientService = indexerClientService;
    }

    public void indexWikiPageToSolr(List<WikipediaPageAvroModel> avroPages) {
        List<WikipediaPageDto> wikiPages = avroPages
                .stream().filter(p -> p != null)
                .map(entityConverter::convertWikiAvroPageToWikiPageDto).toList();
        try {
            boolean result = indexerClientService.insert(wikiPages);
            LOGGER.info("Index result: {}", result);
        } catch (WamsSolrIndexerException e) {
            LOGGER.error("Could not index this batch of docs!", e);
        }
    }

}
