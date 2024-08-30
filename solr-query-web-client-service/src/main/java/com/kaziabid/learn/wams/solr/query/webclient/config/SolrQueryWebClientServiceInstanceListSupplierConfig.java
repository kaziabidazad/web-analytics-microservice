package com.kaziabid.learn.wams.solr.query.webclient.config;

import java.util.List;

import org.springframework.cloud.client.DefaultServiceInstance;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.loadbalancer.core.ServiceInstanceListSupplier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

import com.kaziabid.learn.wams.commonconfig.data.SolrQueryWebClientServiceWebClientConfigData;
import com.kaziabid.learn.wams.commonconfig.data.SolrQueryWebClientServiceWebClientConfigData.WebClientConfigData;

import reactor.core.publisher.Flux;

/**
 * @author Kazi Abid
 */
public class SolrQueryWebClientServiceInstanceListSupplierConfig {

    private final SolrQueryWebClientServiceWebClientConfigData solrQueryWebClientServiceWebClientConfigData;

    public SolrQueryWebClientServiceInstanceListSupplierConfig(
            SolrQueryWebClientServiceWebClientConfigData solrQueryWebClientServiceWebClientConfigData) {
        super();
        this.solrQueryWebClientServiceWebClientConfigData =
                solrQueryWebClientServiceWebClientConfigData;
    }

    @Bean
    @Primary
    ServiceInstanceListSupplier serviceInstanceListSupplier() {
        return new SolrQueryWebClientServiceInstanceListSupplier(
                solrQueryWebClientServiceWebClientConfigData);
    }

}

class SolrQueryWebClientServiceInstanceListSupplier
        implements ServiceInstanceListSupplier {

    private final WebClientConfigData webClientConfigData;

    public SolrQueryWebClientServiceInstanceListSupplier(
            SolrQueryWebClientServiceWebClientConfigData clientConfigData) {
        super();
        this.webClientConfigData = clientConfigData.webClient();
    }

    @Override
    public Flux<List<ServiceInstance>> get() {
        return Flux
                .just(webClientConfigData
                        .instances().stream()
                        .map(i -> (ServiceInstance) new DefaultServiceInstance(i.id(),
                                webClientConfigData.serviceId(), i.host(), i.port(),
                                false))
                        .toList());
    }

    @Override
    public String getServiceId() {
        return webClientConfigData.serviceId();
    }

}