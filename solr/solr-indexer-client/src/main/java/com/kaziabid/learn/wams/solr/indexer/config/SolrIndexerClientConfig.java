package com.kaziabid.learn.wams.solr.indexer.config;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.impl.CloudHttp2SolrClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.kaziabid.learn.wams.commonconfig.data.SolrConfigData;

/**
 * @author Kazi Abid
 */
@Configuration
public class SolrIndexerClientConfig {

    private final SolrConfigData solrConfigData;

    public SolrIndexerClientConfig(SolrConfigData solrConfigData) {
        super();
        this.solrConfigData = solrConfigData;
    }

    @Bean
    public SolrClient solrIndexerClient() {
        return new CloudHttp2SolrClient.Builder(solrConfigData.solrUrls())
                .withDefaultCollection(solrConfigData.wikipediaCollectionName()).build();
    }

}
