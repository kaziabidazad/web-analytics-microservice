package com.kaziabid.learn.wams.solr.indexer.repository.impl;

import java.io.IOException;
import java.util.List;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrServerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.kaziabid.learn.wams.exceptions.solr.WamsSolrIndexerException;
import com.kaziabid.learn.wams.solr.common.entity.WikipediaSolrPage;
import com.kaziabid.learn.wams.solr.indexer.repository.SolrIndexerClientRepository;

/**
 * @author Kazi Abid
 */
@Repository
public class DefaultSolrIndexerClientRepository implements SolrIndexerClientRepository {

    private static final Logger LOGGER =
            LoggerFactory.getLogger(DefaultSolrIndexerClientRepository.class);
    private final SolrClient    solrIndexerClient;

    public DefaultSolrIndexerClientRepository(SolrClient solrIndexerClient) {
        super();
        this.solrIndexerClient = solrIndexerClient;
    }

    @Override
    public boolean insert(WikipediaSolrPage document) throws WamsSolrIndexerException {

        LOGGER
                .info("Starting to Index {} page to solr!",
                        document != null ? document.getDisplayTitle() : "null");
        try {
            var response = solrIndexerClient.addBean(document, 20);
            var status = response.getStatus();
            var responsestatus = response.jsonStr();
            LOGGER
                    .info("Inserted document status : {}, with reponse {}", status,
                            responsestatus);
            return true;
        } catch (IOException | SolrServerException e) {
            try {
                solrIndexerClient.rollback();
                throw new WamsSolrIndexerException(
                        "Error indexing to Solr and all docs have been attepted to rolled back(not guaranteed): ",
                        e);
            } catch (SolrServerException | IOException e1) {
                throw new WamsSolrIndexerException("Error Rolling back docs: ", e1);

            }
        }

    }

    @Override
    public boolean insert(List<WikipediaSolrPage> documents)
            throws WamsSolrIndexerException {
        LOGGER
                .info("Starting to Index {} pages to solr!",
                        documents != null ? documents.size() : 0);
        try {
            var response = solrIndexerClient.addBeans(documents, 50);
            var status = response.getStatus();
            var responsestatus = response.jsonStr();
            LOGGER
                    .info("Inserted documents status : {}, with reponse {}", status,
                            responsestatus);
            return true;
        } catch (IOException | SolrServerException e) {
            LOGGER.error("Error indexing to Solr, Rolling back: ", e);
            try {
                solrIndexerClient.rollback();
                throw new WamsSolrIndexerException(
                        "Error indexing to Solr and rollback was attempted: ", e);
            } catch (SolrServerException | IOException e1) {
                throw new WamsSolrIndexerException("Error Rolling back docs: ", e1);

            }
        }
    }

    @Override
    public boolean update(WikipediaSolrPage document)
            throws WamsSolrIndexerException, IllegalArgumentException {

        LOGGER
                .info("updating Index {} page to solr!",
                        document != null ? document.getDisplayTitle() : "null");
        if (document == null || document.getId() == null || document.getId().isBlank())
            throw new IllegalArgumentException(
                    "Doument ID must be present for update operation.");
        try {
            var response = solrIndexerClient.addBean(document, 50);
            var status = response.getStatus();
            var responsestatus = response.jsonStr();
            LOGGER
                    .info("Updated document status : {}, with reponse {}", status,
                            responsestatus);
            return true;
        } catch (IOException | SolrServerException e) {
            LOGGER.error("Error indexing to Solr, Rolling back: ", e);
            try {
                solrIndexerClient.rollback();
                throw new WamsSolrIndexerException(
                        "Error updating to Solr and then roll back was not attempted: ",
                        e);
            } catch (SolrServerException | IOException e1) {
                throw new WamsSolrIndexerException("Error Rolling back docs: ", e1);

            }
        }

    }

    @Override
    public boolean delete(String documentId) throws WamsSolrIndexerException {
        LOGGER.info("Deleting Index ID: {} page to solr!", documentId);
        try {
            var response = solrIndexerClient.deleteById(documentId, 20);
            var status = response.getStatus();
            var responsestatus = response.jsonStr();
            LOGGER
                    .info("Deleted document status : {}, with reponse {}", status,
                            responsestatus);
            return true;
        } catch (IOException | SolrServerException e) {
            throw new WamsSolrIndexerException(
                    "Error deleting doc from solr for id: " + documentId, e);
        }

    }

}
