package com.kaziabid.learn.wams.solr.client.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.apache.solr.common.params.SolrParams;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kaziabid.learn.wams.common.dto.Page;

/**
 * @author Kazi Abid
 */
class SolrQueryHelperTest {

    private static final Logger LOGGER =
            LoggerFactory.getLogger(SolrQueryHelperTest.class);

    private SolrQueryHelper solrqueryHelper;

    @BeforeEach
    void prepareTest() {
        solrqueryHelper = new SolrQueryHelper();
    }

    /**
     * Test method for
     * {@link com.kaziabid.learn.wams.solr.client.utils.SolrQueryHelper#constructSolrQuery(java.lang.String, com.kaziabid.learn.wams.common.dto.Page)}.
     */
    @Test
    final void testConstructSolrQuery() {
        String query = "Harry Porter";
        Page page = Page.build();
        SolrParams solrParams = solrqueryHelper.constructSolrQuery(query, page);
        assertPageDetailsWithSolrParams(page, solrParams);
        assertDefaultSettingsAndQuery(query, solrParams);
    }

    /**
     * Test method for
     * {@link com.kaziabid.learn.wams.solr.client.utils.SolrQueryHelper#constructSolrPageParam(com.kaziabid.learn.wams.common.dto.Page)}.
     */
    @Test
    final void whenNoPageIsNotProvided_constructSolrparamWithDefaultPageParams() {
        SolrParams solrParams = solrqueryHelper.constructSolrPageParam(null);
        assertNotNull(solrParams);
        Page page = Page.build();
        assertPageDetailsWithSolrParams(page, solrParams);
    }

    @Test
    final void whenDefaultPageIsProvided_constructSolrparamWithDefaultPageParams() {
        Page page = Page.build();
        SolrParams solrParams = solrqueryHelper.constructSolrPageParam(page);
        assertNotNull(solrParams);
        assertPageDetailsWithSolrParams(page, solrParams);
        LOGGER.info("query: {}", solrParams);
    }

    @Test
    final void whenCustomPageIsProvided_constructSolrparamWithDefaultPageParams() {
        Page page = new Page(3, 30, 5);
        SolrParams solrParams = solrqueryHelper.constructSolrPageParam(page);
        assertNotNull(solrParams);
        assertPageDetailsWithSolrParams(page, solrParams);
        LOGGER.info("query: {}", solrParams);
    }

    @Test
    final void whenNavigatingPagesGetCorrectPageValues() {
        int pageNumber = 1;
        int itemPerPage = 10;
        int totalNumberOfPages = 50;
        Page page = Page.build(totalNumberOfPages, itemPerPage);
        assertEquals(pageNumber, page.pageNo());
        assertEquals(itemPerPage, page.itemsPerPage());
        assertEquals(0, solrqueryHelper.getStart(page));
        while (page.canHaveNext()) {
            page = page.nextPage();
            assertEquals(++pageNumber, page.pageNo());
            assertEquals(itemPerPage, page.itemsPerPage());
            assertEquals(page.itemsPerPage() * (page.pageNo() - 1),
                    solrqueryHelper.getStart(page));
        }

    }

    private void assertPageDetailsWithSolrParams(Page expectedPage,
            SolrParams actualParams) {
        String start =
                String.valueOf(expectedPage.itemsPerPage() * (expectedPage.pageNo() - 1));
        assertEquals(start, actualParams.get("start"));
        String rows = String.valueOf(expectedPage.itemsPerPage());
        assertEquals(rows, actualParams.get("rows"));
    }

    private void assertDefaultSettingsAndQuery(String query, SolrParams params) {
        assertEquals(query, params.get("q"));
        assertEquals("lucene", params.get("defType"));
        assertEquals("score desc", params.get("sort"));
        LOGGER.info("query: {}", params);
    }

}
