package com.kaziabid.learn.wams.common.dto;

import java.util.List;

/**
 * @author Kazi Abid
 */
public class PagedResponse<T> implements CommonDTO {

    private Page    page;
    private List<T> data;

    public PagedResponse() {
        page = Page.build();
    }

    public PagedResponse(Page page, List<T> data) {
        super();
        this.page = page;
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

    /**
     * @return the data
     */
    public List<T> getData() {
        return data;
    }

    /**
     * @param data the data to set
     */
    public void setData(List<T> data) {
        this.data = data;
    }

}
