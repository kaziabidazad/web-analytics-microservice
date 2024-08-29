package com.kaziabid.learn.wams.solr.query.webclient.dto;

import com.kaziabid.learn.wams.common.dto.CommonDTO;
import com.kaziabid.learn.wams.common.dto.Page;

/**
 * 
 * @author Kazi Abid
 */
public record QueryRequest(String query, String pageName, Page page)
        implements CommonDTO {

    /**
     * 
     * @return
     */
    public static QueryRequest build() {
        return new QueryRequest("", "", Page.build());
    }

    /**
     * 
     * @param query    - full text query
     * @param pageName - page name to query
     * 
     * @return
     */
    public static QueryRequest build(String anyQuery, String pageName) {
        return new QueryRequest(anyQuery, pageName, Page.build());
    }
}
