package com.kaziabid.learn.wams.solr.common.config;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;

import com.kaziabid.learn.wams.commonconfig.data.SolrConfigData;

import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import reactor.netty.http.client.HttpClient;

/**
 * @author Kazi Abid
 */
@Configuration
public class SolrQueryClientConfig {

    private final SolrConfigData solrConfigData;

    public SolrQueryClientConfig(SolrConfigData solrConfigData) {
        super();
        this.solrConfigData = solrConfigData;
    }

    /**
     * As of solrj:9.6.1 and spring boot 3.3.2,
     * solrj(CloudHttp2SolrClient/CloudSolrClient) is not supported by
     * spring because solrj uses jetty10.x and springboot uses jetty12.x
     * Solr does not support the new jakarta.x.x and therefore cannot be used with
     * springboot3.x.
     * Solr will release new version (10.x) with newer jetty support late 2024.
     * Therefore as a stopgap solution, we will use the outdated
     * spring-data-solr depedency.
     * <br>
     * Use the this bean to use solr when solr start supporting jetty12+
     */
    // @Bean
    // public SolrClient solrClient() {
    // return new CloudHttp2SolrClient.Builder(solrConfigData.solrUrls())
    // .withDefaultCollection(solrConfigData.wikipediaCollectionName()).build();
    // }

    @Bean
    public WebClient solrClient() {
        HttpClient httpClient = HttpClient
                .create().option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000)
                .baseUrl(solrConfigData.solrUrls().get(0))
                .responseTimeout(Duration.ofMillis(5000))
                .doOnConnected(conn -> conn
                        .addHandlerLast(
                                new ReadTimeoutHandler(5000, TimeUnit.MILLISECONDS))
                        .addHandlerLast(
                                new WriteTimeoutHandler(5000, TimeUnit.MILLISECONDS)));

        return WebClient
                .builder().clientConnector(new ReactorClientHttpConnector(httpClient))
                .build();
    }

}
