package com.kaziabid.learn.wams.kafka.producer.service.impl;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.apache.kafka.clients.producer.RecordMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import com.kaziabid.learn.wams.kafka.model.avro.WikipediaPageAvroModel;
import com.kaziabid.learn.wams.kafka.producer.service.KafkaProducer;

import jakarta.annotation.PreDestroy;

@Service
public class WikipediaKafkaProducer
        implements
            KafkaProducer<Long, WikipediaPageAvroModel> {

    private static final Logger LOGGER = LoggerFactory
            .getLogger(WikipediaKafkaProducer.class);

    private KafkaTemplate<Long, WikipediaPageAvroModel> kafkaTemplate;

    /**
     * @param kafkaTemplate
     */
    public WikipediaKafkaProducer(
            KafkaTemplate<Long, WikipediaPageAvroModel> kafkaTemplate) {
        super();
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public void send(String topicName, Long key,
            WikipediaPageAvroModel message) {
        LOGGER.info("Send message to topic = {}", topicName);
        CompletableFuture<SendResult<Long, WikipediaPageAvroModel>> future = kafkaTemplate
                .send(topicName, key,
                        message);
        try {
            SendResult<Long, WikipediaPageAvroModel> result = future.get();
            if (result != null) {
                RecordMetadata recordMetadata = result.getRecordMetadata();
                LOGGER.debug("Received new metadata. Topic: {}; Partition {}; "

                        + "Offset {}; Timestamp {};, at time {}",

                        recordMetadata.topic(),

                        recordMetadata.partition(),

                        recordMetadata.offset(),

                        recordMetadata.timestamp(),

                        System.nanoTime());
            } else {
                LOGGER.error("Error sending Message  to Topic = {}.",
                        topicName);
            }
        } catch (InterruptedException | ExecutionException e) {
            LOGGER.error("Error sending data to topic.", e);
        }

    }

    @Override
    public void send(String topicName, WikipediaPageAvroModel message) {
        LOGGER.info("Send message to topic = {}", topicName);
        CompletableFuture<SendResult<Long, WikipediaPageAvroModel>> future = kafkaTemplate
                .send(topicName, message);
        try {
            SendResult<Long, WikipediaPageAvroModel> result = future.get();
            if (result != null) {
                RecordMetadata recordMetadata = result.getRecordMetadata();
                LOGGER.debug("Received new metadata. Topic: {}; Partition {}; "

                        + "Offset {}; Timestamp {};, at time {}",

                        recordMetadata.topic(),

                        recordMetadata.partition(),

                        recordMetadata.offset(),

                        recordMetadata.timestamp(),

                        System.nanoTime());
            } else {
                LOGGER.error("Error sending Message to Topic = {}.", topicName);
            }
        } catch (InterruptedException | ExecutionException e) {
            LOGGER.error("Error sending data to topic.", e);
        }

    }

    @PreDestroy
    public void close() {
        if (kafkaTemplate != null) {
            LOGGER.info("Closing Kafka producer!");
            kafkaTemplate.destroy();
        }
    }
}
