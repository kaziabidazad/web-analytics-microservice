package com.kaziabid.learn.wams.solr.rx.app.config;

import java.util.concurrent.TimeUnit;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;

import com.kaziabid.learn.wams.commonconfig.data.SolrQueryRxWebClientServiceWebClientConfigData;

import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import reactor.netty.http.client.HttpClient;

/**
 * @author Kazi Abid
 */
@Configuration
@LoadBalancerClients(
        defaultConfiguration = SolrQueryWebClientServiceInstanceListSupplierConfig.class)
public class SolrQueryWebClientConfig {

    private final  SolrQueryRxWebClientServiceWebClientConfigData.WebClientConfigData webClientConfigData;

    public SolrQueryWebClientConfig(
            SolrQueryRxWebClientServiceWebClientConfigData webClientConfigData) {
        super();
        this.webClientConfigData = webClientConfigData.webClient();
    }

    // Load balanced web-client works with builder only.
    @LoadBalanced
    @Bean("solrQueryClientBuilder")
    WebClient.Builder solrQueryClientBuilder() {
        return WebClient
                .builder().baseUrl(webClientConfigData.solrQueryServiceBaseUrl())
                .defaultHeader(HttpHeaders.CONTENT_TYPE,
                        webClientConfigData.contentType())
                .defaultHeader(HttpHeaders.ACCEPT, webClientConfigData.acceptType())
                .clientConnector(
                        new ReactorClientHttpConnector(HttpClient
                                .create()
                                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS,
                                        (int) webClientConfigData.connectTimeoutMs())
                                .doOnConnected(
                                        connection -> connection
                                                .addHandlerLast(new ReadTimeoutHandler(
                                                        webClientConfigData
                                                                .readTimeoutMs(),
                                                        TimeUnit.MILLISECONDS))
                                                .addHandlerLast(new WriteTimeoutHandler(
                                                        webClientConfigData
                                                                .writeTimeoutMs(),
                                                        TimeUnit.MILLISECONDS)))))
                .codecs(clientCodecCnfigurer -> clientCodecCnfigurer
                        .defaultCodecs()
                        .maxInMemorySize(webClientConfigData.maxInMemorySize()));
    }

}
