package com.kaziabid.learn.wams.solr.query.webclient.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.kaziabid.learn.wams.common.dto.PagedResponse;
import com.kaziabid.learn.wams.exceptions.api.WamsRestServiceException;
import com.kaziabid.learn.wams.solr.query.webclient.dto.QueryRequest;
import com.kaziabid.learn.wams.solr.query.webclient.dto.WikipediaSearchQueryResponse;
import com.kaziabid.learn.wams.solr.query.webclient.service.SolrWebClientQueryServce;

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
    public String query(QueryRequest queryRequest, Model model) {
        try {
            LOGGER.info("Request for query: {} ", queryRequest);
            PagedResponse<WikipediaSearchQueryResponse> data =
                    clientQueryServce.query(queryRequest);
            model.addAttribute("queryRequestModel", queryRequest);
            model.addAttribute("queryResponseModel", data);
            return "query";
        } catch (Exception e) {
            throw new WamsRestServiceException(500, e.getMessage());
        }
    }

}
