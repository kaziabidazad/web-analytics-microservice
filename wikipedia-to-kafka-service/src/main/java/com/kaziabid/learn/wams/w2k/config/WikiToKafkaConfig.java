package com.kaziabid.learn.wams.w2k.config;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.kaziabid.learn.wams.common.dto.wiki.WikiPage;
import com.kaziabid.learn.wams.common.sedes.WikipediaPageDeserializer;

/**
 * @author Kazi
 */
@Configuration
@EnableAsync
public class WikiToKafkaConfig {

    @Bean
    public ExecutorService taskExecutor() {
        ExecutorService threadPoolExecutor = (ThreadPoolExecutor) Executors
                .newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        return threadPoolExecutor;
    }
    
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
