package com.kaziabid.learn.wams.k2s.listener.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.kaziabid.learn.wams.commonconfig.data.KafkaWikipediaConsumerSolrConfigData;
import com.kaziabid.learn.wams.k2s.listener.QueueListener;
import com.kaziabid.learn.wams.k2s.solr.service.SolrService;
import com.kaziabid.learn.wams.kafka.model.avro.WikipediaPageAvroModel;

/**
 * @author Kazi
 */
@Component
public class AvroQueueListener implements QueueListener, Runnable {

    private final static Logger LOGGER = LoggerFactory
            .getLogger(AvroQueueListener.class);
    private final BlockingQueue<WikipediaPageAvroModel> wikipediaPageAvroQueue;
    private Thread worker;
    private final SolrService solrService;
    private final KafkaWikipediaConsumerSolrConfigData wikipediaConsumerSolrConfigData;

    /**
     * @param wikipediaPageAvroQueue
     */
    public AvroQueueListener(
            @Autowired @Qualifier(
                "wikipediaAvroQueue"
            ) BlockingQueue<WikipediaPageAvroModel> wikipediaPageAvroQueue,
            SolrService solrService,
            KafkaWikipediaConsumerSolrConfigData wikipediaConsumerSolrConfigData) {
        super();
        this.wikipediaPageAvroQueue = wikipediaPageAvroQueue;
        this.solrService = solrService;
        this.wikipediaConsumerSolrConfigData = wikipediaConsumerSolrConfigData;
    }

    @Override
    public void startListening() {
        worker = new Thread(this, "AvroQueueListener");
        worker.start();
        LOGGER.info("AvroQueueListener Thread started..");
    }

    @Override
    public void run() {
        while (true) {
            List<WikipediaPageAvroModel> kafkaMessages = new ArrayList<>();
            for (int i = 0; i < wikipediaConsumerSolrConfigData
                    .internalQueueMaxSize(); i++) {
                try {
                    kafkaMessages
                            .add(wikipediaPageAvroQueue.take());
                } catch (InterruptedException e) {
                    LOGGER.error(
                            "AvroQueueListener thread interrupted while waiting for take().. ",
                            e);
                }
            }
            //
            solrService.indexWikiPageToSolr(kafkaMessages);
        }
    }

}
