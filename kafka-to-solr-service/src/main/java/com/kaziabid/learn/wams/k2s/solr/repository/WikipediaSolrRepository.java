//package com.kaziabid.learn.wams.k2s.solr.repository;
//
//import java.io.IOException;
//import java.util.List;
//
//import org.apache.solr.client.solrj.SolrClient;
//import org.apache.solr.client.solrj.SolrServerException;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.stereotype.Service;
//
//import com.kaziabid.learn.wams.common.dto.wiki.WikiPage;
//import com.kaziabid.learn.wams.solr.common.entity.SolrPageRequest;
//import com.kaziabid.learn.wams.solr.common.entity.WikipediaSolrPage;
//
///**
// * @author Kazi
// */
//@Service
//public class WikipediaSolrRepository {
//
//    private final static Logger LOGGER = LoggerFactory
//            .getLogger(WikipediaSolrRepository.class);
//
//    private final SolrClient client;
//
//    /**
//     * @param client
//     */
//    public WikipediaSolrRepository(SolrClient client) {
//        super();
//        this.client = client;
//    }
//
//    public List<WikiPage> fullTextAllFields(String searchTerm,
//            SolrPageRequest page) {
//        return null;
//    }
//
//    public void saveAll(List<WikipediaSolrPage> pages) {
//        LOGGER.info("Starting to Index {} pages to solr!",
//                pages != null ? pages.size() : 0);
//        try {
//            client.addBeans(pages);
//            client.commit();
//        } catch (IOException | SolrServerException e) {
//            LOGGER.error("Error indexing to Solr, Rolling back: ", e);
//            try {
//                client.rollback();
//            } catch (SolrServerException | IOException e1) {
//                LOGGER.error("Error Rolling back uncommtted docs: ", e);
//            }
//        }
//    }
//
//}
