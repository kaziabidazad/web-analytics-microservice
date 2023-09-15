package com.kaziabid.learn.wams.w2k.component;

import java.io.IOException;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.hc.client5.http.classic.HttpClient;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.HttpClientBuilder;
import org.apache.hc.core5.http.ClassicHttpResponse;
import org.apache.hc.core5.http.Header;
import org.apache.hc.core5.http.HttpException;
import org.apache.hc.core5.http.HttpHeaders;
import org.apache.hc.core5.http.io.HttpClientResponseHandler;
import org.apache.hc.core5.http.message.BasicHeader;
import org.apache.hc.core5.net.URIBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kaziabid.learn.wams.common.dto.wiki.MostRead;
import com.kaziabid.learn.wams.common.dto.wiki.OnThisDay;
import com.kaziabid.learn.wams.common.dto.wiki.WikiFeedResult;
import com.kaziabid.learn.wams.common.dto.wiki.WikiPage;
import com.kaziabid.learn.wams.commonconfig.data.KafkaWikipediaConfigData;
import com.kaziabid.learn.wams.commonconfig.data.WikipediaConfigData;
import com.kaziabid.learn.wams.kafka.model.avro.WikipediaPageAvroModel;
import com.kaziabid.learn.wams.kafka.producer.service.KafkaProducer;
import com.kaziabid.learn.wams.w2k.service.transformer.WikipediaPageToAvroTransformer;

/**
 * @author Kazi
 */
@Component
public class WikipediaDataExtractor {

    private static final Logger LOGGER = LoggerFactory
            .getLogger(WikipediaDataExtractor.class);

    private final KafkaWikipediaConfigData kafkaWikipediaConfigData;
    private final KafkaProducer<Long, WikipediaPageAvroModel> kafkaProducer;
    private final WikipediaPageToAvroTransformer avroTransformer;
    private final WikipediaConfigData wikipediaConfigData;
    private final ObjectMapper objectMapper;

    public WikipediaDataExtractor(
            KafkaWikipediaConfigData kafkaWikipediaConfigData,
            KafkaProducer<Long, WikipediaPageAvroModel> kafkaProducer,
            WikipediaPageToAvroTransformer avroTransformer,
            WikipediaConfigData wikipediaConfigData, @Autowired @Qualifier(
                "wikiPageObjectMapper"
            ) ObjectMapper objectMapper) {
        this.kafkaWikipediaConfigData = kafkaWikipediaConfigData;
        this.kafkaProducer = kafkaProducer;
        this.avroTransformer = avroTransformer;
        this.wikipediaConfigData = wikipediaConfigData;
        this.objectMapper = objectMapper;
    }

    @Async
    public void extractWikipediaPagePerDay(LocalDate startDate) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter
                .ofPattern(wikipediaConfigData.wikipediadateformat());
        String datepath = startDate.format(dateTimeFormatter);
        LOGGER.info("extracting for date: {}", datepath);

        HttpClient httpClient = HttpClientBuilder.create().build();
        Header acceptHeader = new BasicHeader(HttpHeaders.ACCEPT,
                "application/json");
        try {
            HttpGet getRequest = new HttpGet(
                    new URIBuilder(
                            wikipediaConfigData.wikipediaFeaturedFeedUrl())
                            .appendPath(datepath).build());
            getRequest.addHeader(acceptHeader);
            WikiFeedResult feedResult = httpClient.execute(getRequest,
                    new HttpClientResponseHandler<WikiFeedResult>() {
                        @Override
                        public WikiFeedResult handleResponse(
                                ClassicHttpResponse response)
                                throws HttpException, IOException {
                            String data = new String(response.getEntity()
                                    .getContent().readAllBytes());
                            WikiFeedResult feedResult = objectMapper
                                    .readValue(data, WikiFeedResult.class);
//                    LOGGER.info("Data: {} ",
//                            objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(feedResult));
                            return feedResult;
                        }
                    });
            LOGGER.info("Wiki Feed: {}", feedResult);
            pushFeedresultToKafka(feedResult);
        } catch (URISyntaxException e) {
            LOGGER.error("Error building URI: ", e);
        } catch (IOException e) {
            LOGGER.error("Error executing get request: ", e);
        }
    }

    private void pushFeedresultToKafka(WikiFeedResult feedResult) {
        List<WikipediaPageAvroModel> wikiPages = extractWikiPagesFromFeedResult(
                feedResult);
        wikiPages.forEach(avro -> kafkaProducer
                .send(kafkaWikipediaConfigData.topicName(), avro));
    }

    /**
     * @param feedResult
     * @return
     */
    private List<WikipediaPageAvroModel> extractWikiPagesFromFeedResult(
            WikiFeedResult feedResult) {
        List<WikipediaPageAvroModel> wikiPages = new ArrayList<>();
        WikiPage tfa = feedResult.todaysFetauredArticle();
        wikiPages.add(avroTransformer.getWikipediaPageAvroModelFromPage(tfa));
        MostRead mostRead = feedResult.mostRead();
        if (mostRead != null)
            if (mostRead.articles() != null)
                wikiPages.addAll(mostRead.articles().stream()
                        .map(wikipage -> avroTransformer
                                .getWikipediaPageAvroModelFromPage(wikipage))
                        .collect(Collectors.toList()));
        List<OnThisDay> onthisDayArticles = feedResult.onThisDayArticles();
        if (onthisDayArticles != null) {
            onthisDayArticles.forEach(article -> {
                List<WikiPage> articlePages = article.pages();
                if (articlePages != null) {
                    wikiPages.addAll(articlePages.stream()
                            .map(wikipage -> avroTransformer
                                    .getWikipediaPageAvroModelFromPage(
                                            wikipage))
                            .collect(Collectors.toList()));
                }
            });
        }
        return wikiPages;
    }

}
