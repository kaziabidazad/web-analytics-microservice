package com.kaziabid.learn.wams.t2k.service.impl;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.hc.client5.http.classic.HttpClient;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.config.RequestConfig;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.ClassicHttpResponse;
import org.apache.hc.core5.http.HttpException;
import org.apache.hc.core5.http.io.HttpClientResponseHandler;
import org.apache.hc.core5.net.URIBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.kaziabid.learn.wams.t2k.service.StreamRunner;

import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.sse.SseEventSource;

/**
 * @author Kazi
 */
@Component
public class TwitterKafkaStreamRunner implements StreamRunner {

    public static final Logger LOGGER = LoggerFactory.getLogger(TwitterKafkaStreamRunner.class);

    @Value("${twitter-to-kafka-service.twitter-filter-keywords}")
    List<String> twitterKeywords;

    @Value("${twitter-to-kafka-service.twitterv2-baseuri}")
    private String twitterBasUrl;

    @Value("${twitter-to-kafka-service.twitterv2-rulesuri}")
    private String twitterRulesUrl;

    @Value("${twitter-to-kafka-service.twitterv2-bearer-token}")
    private String twitterBearerToken;

    @Override
    public void start() {
        if (twitterBearerToken == null || twitterBearerToken.isBlank()) {
            LOGGER.error("Bearer token is missing..");
            throw new RuntimeException("Bearer token is missing");
        }
        try {
            getTwitterRules();
        } catch (Exception e) {
            LOGGER.error("Error getting data from twitter rules api..", e);
        }
    }

    @SuppressWarnings("unused")
    private Map<String, String> getRules() {
        Map<String, String> rulesMap = new HashMap<>();
        for (String keyword : twitterKeywords)
            rulesMap.put(keyword, "keyword: " + keyword);
        LOGGER.info("Created filter for twitter stream for keywords: {}", twitterKeywords);
        return rulesMap;
    }

    private void getTwitterRules() throws Exception {
        @SuppressWarnings("unused")
        List<String> rules = new ArrayList<>();
        HttpClient httpClient = HttpClients.custom()
                .setDefaultRequestConfig(RequestConfig.custom().setCookieSpec("STANDARD").build()).build();

        URIBuilder uriBuilder = new URIBuilder(twitterRulesUrl);

        HttpGet httpGet = new HttpGet(uriBuilder.build());
        httpGet.setHeader("Authorization", String.format("Bearer %s", twitterBearerToken));
        httpGet.setHeader("content-type", "application/json");

        httpClient.execute(httpGet, new HttpClientResponseHandler<String>() {

            @Override
            public String handleResponse(ClassicHttpResponse response) throws HttpException, IOException {
                BufferedInputStream inputStream = new BufferedInputStream(response.getEntity().getContent());
                String responseString = new String(inputStream.readAllBytes());
                LOGGER.info(responseString);

                return null;
            }
        });

    }

    public static void jaxrsSseClient() {
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target("https://stream.wikimedia.org/v2/stream/recentchange");
        try (SseEventSource eventSource = SseEventSource.target(target).build()) {
            eventSource.register((event) -> {
                LOGGER.info(event.readData());
            });
            eventSource.open();
            LOGGER.error("After opening event source....");
            LOGGER.error("Second line after opening event source....");
            try {
                Thread.sleep(20000);
            } catch (InterruptedException e) {
                LOGGER.error("Thread interrupted!", e);
            }
        }
        client.close();
    }
}
