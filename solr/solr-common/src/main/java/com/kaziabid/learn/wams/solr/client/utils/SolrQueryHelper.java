package com.kaziabid.learn.wams.solr.client.utils;

import java.util.HashMap;
import java.util.Map;

import org.apache.solr.common.params.MapSolrParams;
import org.apache.solr.common.params.ModifiableSolrParams;
import org.apache.solr.common.params.SolrParams;
import org.springframework.stereotype.Component;

import com.kaziabid.learn.wams.common.dto.Page;

/**
 * @author Kazi Abid
 */
@Component
public class SolrQueryHelper {

    private final Map<String, String> defaultSolrParams;

    public SolrQueryHelper() {
        defaultSolrParams = new HashMap<>();
        defaultSolrParams.put("defType", "lucene");
        defaultSolrParams.put("sort", "score desc");
        defaultSolrParams.put("start", "0");
        defaultSolrParams.put("rows", "10");
    }

    public SolrParams constructSolrQuery(String query, Page page) {
        Map<String, String> queryparam = new HashMap<>();
        queryparam.put("q", query);
        MapSolrParams queryParam = new MapSolrParams(queryparam);
        ModifiableSolrParams solrParams = new ModifiableSolrParams(queryParam);
        solrParams.add(constructSolrPageParam(page));
        return solrParams;
    }

    public SolrParams constructSolrPageParam(Page page) {
        SolrParams solrParam = null;
        if (page == null)
            solrParam = new MapSolrParams(defaultSolrParams);
        else {
            Map<String, String> params = new HashMap<>(defaultSolrParams);
            int itemsPerPage = page.itemsPerPage();
            long start = getStart(page);
            int rows = itemsPerPage;
            params.put("start", String.valueOf(start));
            params.put("rows", String.valueOf(rows));
            solrParam = new MapSolrParams(params);
        }
        return solrParam;
    }

    public long getStart(Page page) {
        return page.itemsPerPage() * (page.pageNo() - 1);
    }
}
