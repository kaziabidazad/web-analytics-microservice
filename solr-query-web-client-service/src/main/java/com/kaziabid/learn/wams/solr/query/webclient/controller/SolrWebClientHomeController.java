package com.kaziabid.learn.wams.solr.query.webclient.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kaziabid.learn.wams.common.dto.AppStatusDTO;
import com.kaziabid.learn.wams.commonconfig.data.SolrQueryWebClientServiceWebClientConfigData;

/**
 * @author Kazi Abid
 */
@RestController
public class SolrWebClientHomeController {

    private final SolrQueryWebClientServiceWebClientConfigData solrQueryWebClientServiceWebClientConfigData;

    public SolrWebClientHomeController(
            SolrQueryWebClientServiceWebClientConfigData solrQueryWebClientServiceWebClientConfigData) {
        super();
        this.solrQueryWebClientServiceWebClientConfigData =
                solrQueryWebClientServiceWebClientConfigData;
    }

    @GetMapping(path = { "/health", "/status" })
    public AppStatusDTO healthyHome() {
        return new AppStatusDTO("UP!", "solr-query-web-client", "./solr-web-client");
    }

    @GetMapping(path = "/info")
    public Object info() {
        return solrQueryWebClientServiceWebClientConfigData;
    }

}
