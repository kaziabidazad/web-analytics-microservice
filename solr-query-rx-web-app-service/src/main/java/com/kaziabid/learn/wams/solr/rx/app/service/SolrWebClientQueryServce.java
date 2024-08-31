package com.kaziabid.learn.wams.solr.rx.app.service;

import java.time.Duration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient.Builder;

import com.kaziabid.learn.wams.common.dto.Page;
import com.kaziabid.learn.wams.common.dto.PagedResponse;
import com.kaziabid.learn.wams.common.dto.solr.WikipediaPageDto;
import com.kaziabid.learn.wams.commonconfig.data.SolrQueryWebClientServiceSolrQueryServiceConfigData;
import com.kaziabid.learn.wams.solr.rx.app.dto.DtoConverter;
import com.kaziabid.learn.wams.solr.rx.app.dto.QueryRequest;
import com.kaziabid.learn.wams.solr.rx.app.dto.ReactivePagedResponse;
import com.kaziabid.learn.wams.solr.rx.app.dto.WikipediaSearchQueryResponse;
import com.kaziabid.learn.wams.solr.rx.app.exception.SolrQebClientException;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author Kazi Abid
 */
@Service
public class SolrWebClientQueryServce {
    private static final Logger LOGGER =
            LoggerFactory.getLogger(SolrWebClientQueryServce.class);

    private final Builder                                             solrWenClientBuilder;
    private final SolrQueryWebClientServiceSolrQueryServiceConfigData queryServiceConfigData;
    private final DtoConverter                                        dtoConverter;

    public SolrWebClientQueryServce(
            @Qualifier(value = "solrQueryClientBuilder") Builder solrWenClientBuilder,
            SolrQueryWebClientServiceSolrQueryServiceConfigData queryServiceConfigData,
            DtoConverter dtoConverter) {
        super();
        this.solrWenClientBuilder = solrWenClientBuilder;
        this.queryServiceConfigData = queryServiceConfigData;
        this.dtoConverter = dtoConverter;
    }

    public ReactivePagedResponse<WikipediaSearchQueryResponse>
            query(QueryRequest request) {
        ReactivePagedResponse<WikipediaSearchQueryResponse> response =
                new ReactivePagedResponse<>();
        LOGGER.info("Query to solr: {} ", request);
        ParameterizedTypeReference<PagedResponse<WikipediaPageDto>> responseType =
                new ParameterizedTypeReference<PagedResponse<WikipediaPageDto>>() {
                };
        String query = request.query() != null && !request.query().isBlank()
                ? request.query() : "*";
        Page requestpage = request.page();
        int itemsPerPage = requestpage != null ? requestpage.itemsPerPage() : 30;
        long pageNumber = requestpage != null ? requestpage.pageNo() : 1;
        Mono<PagedResponse<WikipediaPageDto>> solrResponse = solrWenClientBuilder
                .build().get()
                .uri(uriBuilder -> uriBuilder
                        .queryParam(queryServiceConfigData.query().params().queryParam(),
                                query)
                        .queryParam(
                                queryServiceConfigData
                                        .query().params().itemsPerPageParam(),
                                itemsPerPage)
                        .queryParam(
                                queryServiceConfigData.query().params().pageNumberParam(),
                                pageNumber)
                        .build())
                .retrieve()
                .onStatus(status -> status.equals(HttpStatus.UNAUTHORIZED),
                        clientresponse -> Mono
                                .just(new BadCredentialsException("Not Authenticated.")))
                .onStatus(HttpStatusCode::is4xxClientError, clientresponse -> Mono
                        .just(new SolrQebClientException(clientresponse.toString())))
                .onStatus(HttpStatusCode::is5xxServerError, clientresponse -> Mono
                        .just(new SolrQebClientException(
                                "Internal Server Error" + clientresponse.toString())))
                .bodyToMono(responseType);

        var r = solrResponse.map(pr -> {
            Page page = pr.getPage();
            var responseData = pr.getData();
            response.setPage(page);
            return (Flux
                    .fromIterable(responseData)
                    .map(dtoConverter::convertWikiPageDtoToSearchQueryResponse));
        }).flatMapMany(m -> m).delayElements(Duration.ofMillis(200));
        response.setData(r);
        return response;
    }

}
