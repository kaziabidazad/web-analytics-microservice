package com.kaziabid.learn.wams.w2k.component;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

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
import com.kaziabid.learn.wams.common.dto.wiki.WikiFullPage;
import com.kaziabid.learn.wams.common.dto.wiki.WikiPage;
import com.kaziabid.learn.wams.commonconfig.data.KafkaWikipediaConfigData;
import com.kaziabid.learn.wams.commonconfig.data.UrlParam;
import com.kaziabid.learn.wams.commonconfig.data.WikipediaConfigData;
import com.kaziabid.learn.wams.kafka.model.avro.WikipediaPageAvroModel;
import com.kaziabid.learn.wams.kafka.producer.service.KafkaProducer;
import com.kaziabid.learn.wams.w2k.service.transformer.WikipediaPageToAvroTransformer;

/**
 * @author Kazi
 */
@Component
public class WikipediaDataExtractor {

    private static final Logger LOGGER =
            LoggerFactory.getLogger(WikipediaDataExtractor.class);

    private final KafkaWikipediaConfigData                    kafkaWikipediaConfigData;
    private final KafkaProducer<Long, WikipediaPageAvroModel> kafkaProducer;
    private final WikipediaPageToAvroTransformer              avroTransformer;
    private final WikipediaConfigData                         wikipediaConfigData;
    private final ObjectMapper                                objectMapper;

    public WikipediaDataExtractor(KafkaWikipediaConfigData kafkaWikipediaConfigData,
            KafkaProducer<Long, WikipediaPageAvroModel> kafkaProducer,
            WikipediaPageToAvroTransformer avroTransformer,
            WikipediaConfigData wikipediaConfigData,
            @Autowired @Qualifier("wikiPageObjectMapper") ObjectMapper objectMapper) {
        this.kafkaWikipediaConfigData = kafkaWikipediaConfigData;
        this.kafkaProducer = kafkaProducer;
        this.avroTransformer = avroTransformer;
        this.wikipediaConfigData = wikipediaConfigData;
        this.objectMapper = objectMapper;
    }

    @Async
    public void extractWikipediaPagePerDay(LocalDate startDate) {
        DateTimeFormatter dateTimeFormatter =
                DateTimeFormatter.ofPattern(wikipediaConfigData.wikipediadateformat());
        String datepath = startDate.format(dateTimeFormatter);
        LOGGER.info("extracting for date: {}", datepath);

        HttpClient httpClient = HttpClientBuilder.create().build();
        Header acceptHeader = new BasicHeader(HttpHeaders.ACCEPT, "application/json");
        try {
            HttpGet getRequest = new HttpGet(
                    new URIBuilder(wikipediaConfigData.wikipediaFeaturedFeedUrl())
                            .appendPath(datepath).build());
            getRequest.addHeader(acceptHeader);
            WikiFeedResult feedResult = httpClient
                    .execute(getRequest, new HttpClientResponseHandler<WikiFeedResult>() {
                        @Override
                        public WikiFeedResult handleResponse(ClassicHttpResponse response)
                                throws HttpException, IOException {
                            String data = new String(
                                    response.getEntity().getContent().readAllBytes());
                            return objectMapper.readValue(data, WikiFeedResult.class);
                        }
                    });
            // LOGGER.info("Wiki Feed: {}", feedResult);
            pushFeedresultToKafka(feedResult);
        } catch (URISyntaxException e) {
            LOGGER.error("Error building URI: ", e);
        } catch (IOException e) {
            LOGGER.error("Error executing get request: ", e);
        }
    }

    private void pushFeedresultToKafka(WikiFeedResult feedResult) {
        List<WikipediaPageAvroModel> wikiPages =
                extractWikiPagesFromFeedResult(feedResult);
        wikiPages
                .forEach(avro -> kafkaProducer
                        .send(kafkaWikipediaConfigData.topicName(), avro));
    }

    /**
     * @param feedResult
     * 
     * @return
     */
    private List<WikipediaPageAvroModel>
            extractWikiPagesFromFeedResult(WikiFeedResult feedResult) {
        List<WikipediaPageAvroModel> wikiPages = new ArrayList<>();
        List<WikiPage> allPages = new ArrayList<>();
        WikiPage tfa = feedResult.todaysFetauredArticle();
        allPages.add(tfa);

        MostRead mostRead = feedResult.mostRead();
        if (mostRead != null)
            if (mostRead.articles() != null)
                allPages.addAll(mostRead.articles());
        List<OnThisDay> onthisDayArticles = feedResult.onThisDayArticles();
        if (onthisDayArticles != null) {
            onthisDayArticles.forEach(article -> {
                List<WikiPage> articlePages = article.pages();
                if (articlePages != null)
                    allPages.addAll(articlePages);
            });
        }

        wikiPages.addAll(allPages.stream().filter(p -> p != null).map(wikipage -> {
            String pageTitle = wikipage.title();
            // Extract Full page
            String fullHtmlContent = null;
            if (wikipediaConfigData.indexCompletePage()) {
                fullHtmlContent = extractFullHtmlWikiContent(pageTitle);
            }
            WikiFullPage wikiFullPage = new WikiFullPage(wikipage, fullHtmlContent);
            return avroTransformer.getWikipediaPageAvroModelFromPage(wikiFullPage);
        }).toList());

        return wikiPages;
    }

    private String extractFullHtmlWikiContent(String pageTitle) {
        String fullPageHtml = null;
        List<UrlParam> params = wikipediaConfigData.wikipediaPageHtmlApi().urlParams();
        if (params == null) {
            LOGGER
                    .warn("Url params config is not found.. Cannot extract full page content..");
            return null;
        }
        final URIBuilder uriBuilder;
        try {
            uriBuilder =
                    new URIBuilder(wikipediaConfigData.wikipediaPageHtmlApi().baseUrl());
        } catch (URISyntaxException e) {
            LOGGER
                    .error("Error building uri from {}  Cannot extract full page content..: ",
                            wikipediaConfigData.wikipediaPageHtmlApi().baseUrl(), e);
            return null;
        }
        uriBuilder.appendPath(pageTitle);
        params.stream().forEach(p -> uriBuilder.addParameter(p.name(), p.value()));
        try {
            URI wikipediaFullPageUri = uriBuilder.build();
            HttpGet getRequest = new HttpGet(wikipediaFullPageUri);
            HttpClient httpClient = HttpClientBuilder.create().build();
            fullPageHtml = httpClient
                    .execute(getRequest, new HttpClientResponseHandler<String>() {
                        @Override
                        public String handleResponse(ClassicHttpResponse response)
                                throws HttpException, IOException {
                            return new String(
                                    response.getEntity().getContent().readAllBytes());
                        }
                    });
        } catch (URISyntaxException | IOException e) {
            LOGGER
                    .error("Error calling wiki page api:  Cannot extract full page content.. ",
                            e);
        }
        return fullPageHtml;
    }

}
