package com.kaziabid.learn.wams.k2s.solr.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kaziabid.learn.wams.k2s.converter.EntityConverter;
import com.kaziabid.learn.wams.k2s.entity.WikipediaSolrPage;
import com.kaziabid.learn.wams.k2s.solr.repository.WikipediaSolrRepository;
import com.kaziabid.learn.wams.kafka.model.avro.WikipediaPageAvroModel;

/**
 * @author Kazi
 */
@Service
public class SolrService {

    @SuppressWarnings("unused")
    private final ObjectMapper objectMapper;
    private final EntityConverter entityConverter;
    private final WikipediaSolrRepository wikipediaSolrRepository;

    /**
     * 
     * @param objectMapper
     * @param entityConverter
     */
    public SolrService(
            @Autowired @Qualifier(
                "wikiPageObjectMapper"
            ) ObjectMapper objectMapper,
            EntityConverter entityConverter,
            WikipediaSolrRepository wikipediaSolrRepository) {
        super();
        this.objectMapper = objectMapper;
        this.entityConverter = entityConverter;
        this.wikipediaSolrRepository = wikipediaSolrRepository;
    }

    public void indexWikiPageToSolr(List<WikipediaPageAvroModel> avroPages) {

        List<WikipediaSolrPage> wikiPages = avroPages.stream()
                .filter(p -> p != null)
                .map(p -> {
                    return entityConverter.convertWikiAvroPageToWikiSolrPage(p);
                }).collect(Collectors.toList());

        wikipediaSolrRepository.saveAll(wikiPages);
    }

}
