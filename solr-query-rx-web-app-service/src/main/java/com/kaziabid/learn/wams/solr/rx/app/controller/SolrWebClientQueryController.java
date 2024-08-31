package com.kaziabid.learn.wams.solr.rx.app.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.thymeleaf.spring6.context.webflux.IReactiveSSEDataDriverContextVariable;
import org.thymeleaf.spring6.context.webflux.ReactiveDataDriverContextVariable;

import com.kaziabid.learn.wams.exceptions.api.WamsRestServiceException;
import com.kaziabid.learn.wams.solr.rx.app.dto.QueryRequest;
import com.kaziabid.learn.wams.solr.rx.app.dto.ReactivePagedResponse;
import com.kaziabid.learn.wams.solr.rx.app.dto.WikipediaSearchQueryResponse;
import com.kaziabid.learn.wams.solr.rx.app.service.SolrWebClientQueryServce;

/**
 * @author Kazi Abid
 */
@Controller
@RequestMapping(path = "/query")
public class SolrWebClientQueryController {
    private static final Logger            LOGGER =
            LoggerFactory.getLogger(SolrWebClientQueryController.class);
    private final SolrWebClientQueryServce clientQueryServce;

    public SolrWebClientQueryController(SolrWebClientQueryServce clientQueryServce) {
        super();
        this.clientQueryServce = clientQueryServce;
    }

    @GetMapping
    public String query(Model model) {
        model.addAttribute("queryRequestModel", QueryRequest.build());
        return "query";
    }

    @PostMapping(
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE + ";charset=UTF-8")
    public String query(QueryRequest queryRequest, Model model,
            ServerHttpResponse httpResponse) {
        try {
            LOGGER.info("Request for query: {} ", queryRequest);
            ReactivePagedResponse<WikipediaSearchQueryResponse> data =
                    clientQueryServce.query(queryRequest);
            httpResponse
                    .getHeaders().add("pageNo", String.valueOf(data.getPage().pageNo()));
            httpResponse
                    .getHeaders()
                    .add("totalPages", String.valueOf(data.getPage().totalPages()));
            httpResponse
                    .getHeaders()
                    .add("itemsPerPage", String.valueOf(data.getPage().itemsPerPage()));
            IReactiveSSEDataDriverContextVariable reactiveData =
                    new ReactiveDataDriverContextVariable(data.getData(), 1);
            model.addAttribute("queryRequestModel", queryRequest);
            model.addAttribute("queryResponseModel", reactiveData);
            return "query";
        } catch (Exception e) {
            throw new WamsRestServiceException(500, e.getMessage());
        }
    }

}
