package com.kaziabid.learn.wams.k2s.config;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.kaziabid.learn.wams.common.dto.wiki.WikiPage;
import com.kaziabid.learn.wams.common.sedes.WikipediaPageDeserializer;
import com.kaziabid.learn.wams.commonconfig.data.KafkaWikipediaConsumerSolrConfigData;
import com.kaziabid.learn.wams.kafka.model.avro.WikipediaPageAvroModel;

/**
 * @author Kazi
 */
@Configuration
public class kafkaToSolrServiceConfig {

    private final static Logger LOGGER = LoggerFactory
            .getLogger(kafkaToSolrServiceConfig.class);
    private final KafkaWikipediaConsumerSolrConfigData consumerSolrConfigData;

    /**
     * @param consumerSolrConfigData
     */
    public kafkaToSolrServiceConfig(
            KafkaWikipediaConsumerSolrConfigData consumerSolrConfigData) {
        super();
        this.consumerSolrConfigData = consumerSolrConfigData;
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

    @Bean("wikipediaAvroQueue")
    public BlockingQueue<WikipediaPageAvroModel> wikipediaAvroQueue() {
        BlockingQueue<WikipediaPageAvroModel> wikipediaPageAvroModelQueue = new LinkedBlockingDeque<>(
                consumerSolrConfigData.internalQueueMaxSize());
        LOGGER.info("********************************************************");
        LOGGER.info("    Created a bean of LinkedBlockingQueue of size {} ",
                consumerSolrConfigData.internalQueueMaxSize());
        LOGGER.info("********************************************************");
        return wikipediaPageAvroModelQueue;
    }

}
