package com.kaziabid.learn.wams.solr.query.service.controller;

import java.util.Optional;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kaziabid.learn.wams.common.dto.CommonHttpResponse;
import com.kaziabid.learn.wams.common.dto.PagedResponse;
import com.kaziabid.learn.wams.common.dto.solr.WikipediaPageDto;
import com.kaziabid.learn.wams.exceptions.api.WamsRestServiceException;
import com.kaziabid.learn.wams.exceptions.solr.WamsSolrQueryException;
import com.kaziabid.learn.wams.solr.query.service.service.SolrQueryService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

/**
 * @author Kazi Abid
 */
@RestController
@RequestMapping(path = "/docs")
public class SolrQueryController {
    private final SolrQueryService solrService;

    public SolrQueryController(SolrQueryService solrService) {
        super();
        this.solrService = solrService;
    }

    @Operation(description = "Solr Query API", summary = "Text Search API.")
    @ApiResponses(
            value = { @ApiResponse(responseCode = "200", description = "Search was done."
            // No content specified as we are having generic type is response. We want
            // swagger to auto-generate
            ), @ApiResponse(
                    responseCode = "500",
                    description = "Server error happened during search",
                    content = { @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = CommonHttpResponse.class)) }

            ) })

    @GetMapping
    public PagedResponse<WikipediaPageDto> query(
            @RequestParam(
                    name = "${solr-query-service.query.params.query-param}") Optional<
                            String> queryParam,
            @RequestParam(
                    name = "${solr-query-service.query.params.page-number-param}") Optional<
                            Integer> pageNumberParam,
            @RequestParam(
                    name = "${solr-query-service.query.params.items-per-page-param}") Optional<
                            Integer> itemsPerPageParam) {
        PagedResponse<WikipediaPageDto> response = new PagedResponse<>();
        try {
            String q = !queryParam.isEmpty() ? queryParam.get() : "*";
            int itemsPerPage = itemsPerPageParam.isEmpty() ? 10 : itemsPerPageParam.get();
            int pageNum = pageNumberParam.isEmpty() ? 1 : pageNumberParam.get();
            if (itemsPerPage == 0)
                response = solrService.query();
            else
                response = solrService.query(q, itemsPerPage, pageNum);
        } catch (WamsSolrQueryException e) {
            throw new WamsRestServiceException(500, e.getMessage(), e);
        }
        return response;
    }

}
