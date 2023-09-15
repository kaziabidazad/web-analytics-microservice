package com.kaziabid.learn.wams.k2s.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.kaziabid.learn.wams.common.dto.wiki.WikiPage;
import com.kaziabid.learn.wams.common.sedes.WikipediaPageDeserializer;

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

}
