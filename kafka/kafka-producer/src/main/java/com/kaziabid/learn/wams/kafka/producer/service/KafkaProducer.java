package com.kaziabid.learn.wams.kafka.producer.service;

import java.io.Serializable;

import org.apache.avro.specific.SpecificRecordBase;

/**
 * @author Kazi
 */
public interface KafkaProducer<K extends Serializable, V extends SpecificRecordBase> {

    void send(String topicName, K key, V message);

    void send(String topicName, V message);
}
