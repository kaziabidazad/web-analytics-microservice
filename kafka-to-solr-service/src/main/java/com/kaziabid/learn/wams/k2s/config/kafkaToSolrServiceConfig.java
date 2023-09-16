package com.kaziabid.learn.wams.k2s.config;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.kaziabid.learn.wams.common.dto.wiki.WikiPage;
import com.kaziabid.learn.wams.common.sedes.WikipediaPageDeserializer;
import com.kaziabid.learn.wams.kafka.model.avro.WikipediaPageAvroModel;

/**
 * @author Kazi
 */
@Configuration
public class kafkaToSolrServiceConfig {

    @Bean("wikiPageObjectMapper")
    public ObjectMapper wikiPageObjectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        SimpleModule wikiPageModule = new SimpleModule();
        wikiPageModule.addDeserializer(WikiPage.class,
                new WikipediaPageDeserializer());
        mapper.registerModule(wikiPageModule);
        return mapper;
    }

    @Bean("wikipediaAvroQueue")
    public BlockingQueue<WikipediaPageAvroModel> wikipediaAvroQueue() {
        BlockingQueue<WikipediaPageAvroModel> wikipediaPageAvroModelQueue = new LinkedBlockingDeque<>(
                100);
        return wikipediaPageAvroModelQueue;
    }

}
