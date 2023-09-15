package com.kaziabid.learn.wams.k2e.consumer.service;

import java.io.Serializable;
import java.util.List;

import org.apache.avro.specific.SpecificRecordBase;

import com.kaziabid.learn.wams.kafka.model.avro.WikipediaPageAvroModel;

/**
 * @author Kazi
 */
public interface KafkaConsumer<K extends Serializable, V extends SpecificRecordBase> {

    /**
     * 
     * @param messages
     * @param keys
     * @param partitions
     * @param offsets
     */
    void receive(List<WikipediaPageAvroModel> messages, List<Integer> partitions,
            List<Long> offsets);
}
