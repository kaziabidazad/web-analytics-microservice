package com.kaziabid.learn.wams.solr.rx.app.controller;

import java.time.Duration;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kaziabid.learn.wams.common.dto.AppStatusDTO;
import com.kaziabid.learn.wams.commonconfig.data.SolrQueryWebClientServiceWebClientConfigData;
import com.kaziabid.learn.wams.solr.rx.app.dto.QueryRequest;
import com.kaziabid.learn.wams.solr.rx.app.dto.WikipediaSearchQueryResponse;
import com.kaziabid.learn.wams.solr.rx.app.service.SolrWebClientQueryServce;

import reactor.core.publisher.Flux;

/**
 * @author Kazi Abid
 */
@Controller
public class SolrWebClientHomeController {

    private final SolrQueryWebClientServiceWebClientConfigData solrQueryWebClientServiceWebClientConfigData;
    private final SolrWebClientQueryServce                     clientQueryServce;

    public SolrWebClientHomeController(
            SolrQueryWebClientServiceWebClientConfigData solrQueryWebClientServiceWebClientConfigData,
            SolrWebClientQueryServce clientQueryServce) {
        super();
        this.solrQueryWebClientServiceWebClientConfigData =
                solrQueryWebClientServiceWebClientConfigData;
        this.clientQueryServce = clientQueryServce;
    }

    @GetMapping(path = { "/health", "/status" })
    @ResponseBody
    public AppStatusDTO healthyHome() {
        return new AppStatusDTO("UP!", "solr-query-web-client", "./solr-web-client");
    }

    @GetMapping(path = "/info")
    @ResponseBody
    public Object info() {
        return solrQueryWebClientServiceWebClientConfigData;
    }

    @GetMapping(path = { "/", "/home" })
    public String home() {
        return "index";
    }

    @GetMapping(path = "/error")
    public String error() {
        return "error";
    }

    @GetMapping(path = "/flux")
    public Flux<Integer> flux() {
        return Flux.generate(() -> 1, (state, sink) -> {
            int nextVal = state + 1;
            sink.next(nextVal);
            if (nextVal == 100000)
                sink.complete();
            return nextVal;
        }).delayElements(Duration.ofMillis(400)).map(i -> (int) i).log()
                .onErrorComplete();
    }

    @GetMapping(path = "/restQuery")
    public Flux<WikipediaSearchQueryResponse> query() {
        return clientQueryServce.query(QueryRequest.build()).getData();
    }
}
