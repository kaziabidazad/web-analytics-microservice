package com.kaziabid.learn.wams.k2s.consumer.service.impl;

import java.util.List;
import java.util.concurrent.BlockingQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.config.KafkaListenerEndpointRegistry;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import com.kaziabid.learn.wams.commonconfig.data.KafkaWikipediaConfigData;
import com.kaziabid.learn.wams.commonconfig.data.KafkaWikipediaConsumerSolrConfigData;
import com.kaziabid.learn.wams.k2s.consumer.service.KafkaSolrConsumer;
import com.kaziabid.learn.wams.kafka.model.avro.WikipediaPageAvroModel;
import com.kaziabid.learn.wams.kafkaadmin.clients.KafkaWikipediaAdminClient;

/**
 * @author Kazi
 */
@Component
public class WikipediaKafkaToSolrConsumer
        implements
            KafkaSolrConsumer<Long, WikipediaPageAvroModel> {

    private static final Logger LOGGER = LoggerFactory
            .getLogger(WikipediaKafkaToSolrConsumer.class);
    private final KafkaListenerEndpointRegistry kafkaListenerEndpointRegistry;
    private final KafkaWikipediaAdminClient kafkaWikipediaAdminClient;
    private final KafkaWikipediaConfigData kafkaWikipediaConfigData;
//    private final String listerID = "wikipediaPagesToSolrTopicListenerTest001";
    private final BlockingQueue<WikipediaPageAvroModel> wikipediaAvroQueue;
    private final KafkaWikipediaConsumerSolrConfigData kafkaWikipediaConsumerSolrConfigData;

    /**
     * @param kafkaListenerEndpointRegistry
     * @param kafkaWikipediaAdminClient
     * @param kafkaWikipediaConfigData
     */
    public WikipediaKafkaToSolrConsumer(
            KafkaListenerEndpointRegistry kafkaListenerEndpointRegistry,
            KafkaWikipediaAdminClient kafkaWikipediaAdminClient,
            KafkaWikipediaConfigData kafkaConfigData,
            @Autowired @Qualifier(
                "wikipediaAvroQueue"
            ) BlockingQueue<WikipediaPageAvroModel> wikipediaAvroQueue,
            KafkaWikipediaConsumerSolrConfigData kafkaWikipediaConsumerSolrConfigData) {
        super();
        this.kafkaListenerEndpointRegistry = kafkaListenerEndpointRegistry;
        this.kafkaWikipediaAdminClient = kafkaWikipediaAdminClient;
        this.kafkaWikipediaConfigData = kafkaConfigData;
        this.wikipediaAvroQueue = wikipediaAvroQueue;
        this.kafkaWikipediaConsumerSolrConfigData = kafkaWikipediaConsumerSolrConfigData;
    }

    @EventListener(value = ApplicationStartedEvent.class)
    public void onStartup() {
        kafkaWikipediaAdminClient.checkTopicsCreated();
        LOGGER.info("Topics with name {} is ready for operation!",
                kafkaWikipediaConfigData.topicNamesToCreate().toArray());
        kafkaListenerEndpointRegistry
                .getListenerContainer(
                        kafkaWikipediaConsumerSolrConfigData.consumerGroupId())
                .start();
    }

    @Override
    @KafkaListener(
            id = "${kafka-consumer-solr-config.consumer-group-id}",
            topics = "${kafka-config.wikipedia.topic-name}",
            concurrency = "${kafka-consumer-solr-config.concurrency}"
    )
    public void receive(@Payload List<WikipediaPageAvroModel> messages,
            @Header(KafkaHeaders.RECEIVED_PARTITION) List<Integer> partitions,
            @Header(KafkaHeaders.OFFSET) List<Long> offsets) {

        LOGGER.info(
                "{} number of messages received with partitions {} and offsets {}, "
                        + "sending it to elastic: Thread id: {}",
                messages.size(), partitions.toString(), offsets.toString(),
                Thread.currentThread().getId());
        messages.stream().filter(m -> m != null)
                .forEach(m -> {
                    try {
                        wikipediaAvroQueue.put(m);
                    } catch (InterruptedException e) {
                        LOGGER.error(
                                "Error adding message to the blocking queue for pageId: {} and Title: {} ",
                                m.getPageId(), m.getTitle());
                    }
                });
    }

    /*
     * public static void main(String[] args) { String json =
     * "{\"content_urls\":{\"desktop\":{\"edit\":\"https://en.wikipedia.org/wiki/History_of_timekeeping_devices?action=edit\",\"page\":\"https://en.wikipedia.org/wiki/History_of_timekeeping_devices\",\"revisions\":\"https://en.wikipedia.org/wiki/History_of_timekeeping_devices?action=history\",\"talk\":\"https://en.wikipedia.org/wiki/Talk:History_of_timekeeping_devices\"},\"mobile\":{\"edit\":\"https://en.m.wikipedia.org/wiki/History_of_timekeeping_devices?action=edit\",\"page\":\"https://en.m.wikipedia.org/wiki/History_of_timekeeping_devices\",\"revisions\":\"https://en.m.wikipedia.org/wiki/Special:History/History_of_timekeeping_devices\",\"talk\":\"https://en.m.wikipedia.org/wiki/Talk:History_of_timekeeping_devices\"}},\"dir\":\"ltr\",\"displaytitle\":\"<span class=\\\"mw-page-title-main\\\">History of timekeeping devices</span>\",\"extract\":\"The history of timekeeping devices dates back to when ancient civilizations first observed astronomical bodies as they moved across the sky. Devices and methods for keeping time have gradually improved through a series of new inventions, starting with measuring time by continuous processes, such as the flow of liquid in water clocks, to mechanical clocks, and eventually repetitive, oscillatory processes, such as the swing of pendulums. Oscillating timekeepers are used in all modern timepieces.\",\"extract_html\":\"<p>The history of timekeeping devices dates back to when ancient civilizations first observed astronomical bodies as they moved across the sky. Devices and methods for keeping time have gradually improved through a series of new inventions, starting with measuring time by continuous processes, such as the flow of liquid in water clocks, to mechanical clocks, and eventually repetitive, oscillatory processes, such as the swing of pendulums. Oscillating timekeepers are used in all modern timepieces.</p>\",\"lang\":\"en\",\"namespace\":{\"id\":0,\"text\":\"\"},\"normalizedtitle\":\"History of timekeeping devices\",\"originalimage\":{\"height\":3819,\"source\":\"https://upload.wikimedia.org/wikipedia/commons/7/70/Marine_sandglass_MMM.jpg\",\"width\":1637},\"pageid\":14449116,\"revision\":\"1158272784\",\"thumbnail\":{\"height\":1493,\"source\":\"https://upload.wikimedia.org/wikipedia/commons/thumb/7/70/Marine_sandglass_MMM.jpg/640px-Marine_sandglass_MMM.jpg\",\"width\":640},\"tid\":\"cc069720-398d-11ee-a14f-476681d0bb9d\",\"timestamp\":\"2023-06-03T01:22:32Z\",\"title\":\"History_of_timekeeping_devices\",\"titles\":{\"canonical\":\"History_of_timekeeping_devices\",\"display\":\"<span class=\\\"mw-page-title-main\\\">History of timekeeping devices</span>\",\"normalized\":\"History of timekeeping devices\"},\"type\":\"standard\",\"wikibase_item\":\"Q1321131\"}"
     * ; ObjectMapper mapper = new ObjectMapper(); SimpleModule wikiPageModule =
     * new SimpleModule(); wikiPageModule.addDeserializer(WikiPage.class, new
     * WikipediaPageDeserializer()); mapper.registerModule(wikiPageModule); try
     * { // WikiFeedResult page = mapper.readValue(new BufferedInputStream(new
     * FileInputStream( //
     * "D:\\works\\workspaces\\EclipseWorkspaces\\POC\\web-analytics-microservice\\docs\\wiki_response.json"
     * )), // WikiFeedResult.class); // System.out.println(page); WikiPage page
     * = mapper.readValue(json, WikiPage.class);
     * System.out.println(mapper.writerWithDefaultPrettyPrinter()
     * .writeValueAsString(page)); } catch (IOException e) {
     * e.printStackTrace(); } }
     */
}
