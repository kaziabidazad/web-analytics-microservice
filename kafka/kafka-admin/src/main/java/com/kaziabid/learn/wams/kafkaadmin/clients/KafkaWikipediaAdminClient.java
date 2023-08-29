package com.kaziabid.learn.wams.kafkaadmin.clients;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.CreateTopicsResult;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.admin.TopicListing;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.retry.RetryContext;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import com.kaziabid.learn.wams.commonconfig.data.KafkaWikipediaConfigData;
import com.kaziabid.learn.wams.commonconfig.data.RetryConfigData;
import com.kaziabid.learn.wams.kafkaadmin.exception.KafkaClientException;

import reactor.core.publisher.Mono;

/**
 * @author Kazi
 */
@Component
public class KafkaWikipediaAdminClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaWikipediaAdminClient.class);

    private final KafkaWikipediaConfigData kafkaWikipediaConfigData;

    private final RetryConfigData retryConfigData;

    private final AdminClient adminClient;

    private final RetryTemplate retryTemplate;

    private final WebClient webClient;

    /**
     * @param kafkaWikipediaConfigData
     * @param retryConfigData
     * @param adminClient
     * @param retryTemplate
     */
    public KafkaWikipediaAdminClient(KafkaWikipediaConfigData kafkaWikipediaConfigData, RetryConfigData retryConfigData,
            AdminClient adminClient, RetryTemplate retryTemplate, WebClient webClient) {
        super();
        this.kafkaWikipediaConfigData = kafkaWikipediaConfigData;
        this.retryConfigData = retryConfigData;
        this.adminClient = adminClient;
        this.retryTemplate = retryTemplate;
        this.webClient = webClient;
    }

    public void createTopics() {
        try {
            retryTemplate.execute(this::doCreateTopics);
        } catch (Throwable t) {
            throw new KafkaClientException("Reached maximum number of retries for creating kafka topics(s)!", t);
        }
        checkTopicsCreated();
    }

    private void checkTopicsCreated() {
        Collection<TopicListing> topics = getTopics();
        int retryCount = 1;
        Integer maxRetry = retryConfigData.getMaxAttempts();
        Integer multiplier = retryConfigData.getMultiplier().intValue();
        Long sleepTime = retryConfigData.getSleepTimeMs();
        for (String topicName : kafkaWikipediaConfigData.topicsNamesToCreate()) {
            while (!isTopicCreated(topics, topicName)) {
                checkMaxRetry(retryCount++, maxRetry);
                sleep(sleepTime);
                sleepTime *= multiplier;
                topics = getTopics();
            }
        }
    }

    public void checkSchemaRegistry() {
        int retryCount = 1;
        Integer maxRetry = retryConfigData.getMaxAttempts();
        Integer multiplier = retryConfigData.getMultiplier().intValue();
        Long sleepTime = retryConfigData.getSleepTimeMs();
        while (!getSchemaRegistryStatus().is2xxSuccessful()) {
            checkMaxRetry(retryCount++, maxRetry);
            sleep(sleepTime);
            sleepTime *= multiplier;
        }

    }

    private HttpStatus getSchemaRegistryStatus() {
        try {
            return (HttpStatus) webClient.method(HttpMethod.GET)

                    .uri(kafkaWikipediaConfigData.schemaRegistryUrl())

                    .exchangeToMono(response -> {
                        if (response.statusCode().is2xxSuccessful()) {
                            return Mono.just(response.statusCode());
                        } else {
                            return Mono.just(HttpStatus.SERVICE_UNAVAILABLE);
                        }
                    }).block();
        } catch (Exception e) {
            return HttpStatus.SERVICE_UNAVAILABLE;
        }
    }

    /**
     * 
     * @param sleepTime
     * @throws KafkaClientException
     */
    private void sleep(Long sleepTime) throws KafkaClientException {
        try {
            Thread.sleep(sleepTime);
        } catch (InterruptedException e) {
            throw new KafkaClientException("Error while sleeping for waiting for new craeted topics.");
        }

    }

    /**
     * 
     * @param retryCount
     * @param maxRetry
     * @throws KafkaClientException
     */
    private void checkMaxRetry(int retryCount, Integer maxRetry) throws KafkaClientException {
        if (retryCount > maxRetry)
            throw new KafkaClientException("Exceeded the max retry limit.");
    }

    /**
     * 
     * @param topics    List of topics available in the Kafka Cluster
     * @param topicName - The topic name to check
     * @return
     */
    private boolean isTopicCreated(Collection<TopicListing> topics, String topicName) {
        if (topics == null || topics.isEmpty())
            return false;
        return topics.stream().anyMatch(t -> t.name().equals(topicName));
    }

    private CreateTopicsResult doCreateTopics(RetryContext retryContext) {
        List<String> topicNames = kafkaWikipediaConfigData.topicsNamesToCreate();
        LOGGER.info("Creating {} topic(s): {} , attempt {} ", topicNames.size(), topicNames,
                retryContext.getRetryCount());
        List<NewTopic> kafkaTopics = topicNames.stream().map(t -> {
            NewTopic newTopic = new NewTopic(t.trim(), kafkaWikipediaConfigData.numberOfpartitions(),
                    kafkaWikipediaConfigData.replicationFactor());
            return newTopic;
        }).collect(Collectors.toList());
        return adminClient.createTopics(kafkaTopics);
    }

    private Collection<TopicListing> getTopics() {
        Collection<TopicListing> topics;
        try {
            topics = retryTemplate.execute(this::doGetTopics);
        } catch (Throwable t) {
            throw new KafkaClientException("Reached maximum number of retries for reading kafka topics(s)!", t);
        }
        return topics;
    }

    private Collection<TopicListing> doGetTopics(RetryContext retryContext)
            throws InterruptedException, ExecutionException {
        LOGGER.info("Reading Kafka Topic{}, attempt: {}", kafkaWikipediaConfigData.topicsNamesToCreate(),
                retryContext.getRetryCount());
        Collection<TopicListing> topics = adminClient.listTopics().listings().get();
        if (topics != null)
            topics.forEach(topic -> LOGGER.info("Topic with name {}", topic.name()));
        return topics;

    }

    public void testRetry() {
        retryTemplate.execute(this::testRetryAction);
    }

    private boolean testRetryAction(RetryContext context) {
        LOGGER.info("Retrying {}", context.getRetryCount());
        return false;
    }

}
