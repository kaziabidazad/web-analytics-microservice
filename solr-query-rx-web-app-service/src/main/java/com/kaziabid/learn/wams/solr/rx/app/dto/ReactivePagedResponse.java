package com.kaziabid.learn.wams.solr.rx.app.dto;

import com.kaziabid.learn.wams.common.dto.CommonDTO;
import com.kaziabid.learn.wams.common.dto.Page;

import reactor.core.publisher.Flux;

/**
 * @author Kazi Abid
 */
public class ReactivePagedResponse<T> implements CommonDTO {

    private Page    page;
    private Flux<T> data;

    public ReactivePagedResponse() {
        this.page = Page.build();
    }

    /**
     * @param page
     * @param data
     */
    public ReactivePagedResponse(Page page, Flux<T> data) {
        this.page = page;
        this.data = data;
    }

    /**
     * @return the data
     */
    public Flux<T> getData() {
        return data;
    }

    /**
     * @param data the data to set
     */
    public void setData(Flux<T> data) {
        this.data = data;
    }

    /**
     * @return the page
     */
    public Page getPage() {
        return page;
    }

    /**
     * @param page the page to set
     */
    public void setPage(Page page) {
        this.page = page;
    }

}
