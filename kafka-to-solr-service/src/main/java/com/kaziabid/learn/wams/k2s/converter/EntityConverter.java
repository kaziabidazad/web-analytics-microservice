package com.kaziabid.learn.wams.k2s.converter;

import org.springframework.stereotype.Component;

import com.kaziabid.learn.wams.common.dto.solr.WikipediaPageDto;
import com.kaziabid.learn.wams.commonconfig.data.KafkaToSolrConfigData;
import com.kaziabid.learn.wams.kafka.model.avro.WikipediaPageAvroModel;
import com.kaziabid.learn.wams.solr.common.entity.WikipediaSolrPage;

/**
 * @author Kazi
 */
@Component
public class EntityConverter {

    private final KafkaToSolrConfigData kafkaToSolrConfigData;

    /**
     * @param kafkaToSolrConfigData
     */
    public EntityConverter(KafkaToSolrConfigData kafkaToSolrConfigData) {
        super();
        this.kafkaToSolrConfigData = kafkaToSolrConfigData;
    }

    public WikipediaSolrPage
            convertWikiAvroPageToWikiSolrPage(WikipediaPageAvroModel avroModel) {
        WikipediaSolrPage page = null;
        if (avroModel != null) {
            String id = avroModel.getTid();
            String lang = avroModel.getLang();
            String type = avroModel.getType();
            String title = avroModel.getTitle();
            String displayTitle = avroModel.getDisplayTitle();
            String normalizedTitle = avroModel.getNormalizedTitle();
            Long pageId = avroModel.getPageId();
            String tid = avroModel.getTid();
            String wikibaseItem = avroModel.getWikibaseItem();
            String extract = avroModel.getExtract();
            String extractHtml = avroModel.getExtractHtml();
            String fullPage = null;
            if (kafkaToSolrConfigData.indexCompletePage()) {
                avroModel.getFullPage();
            }
            String timestamp = avroModel.getTimestamp();
            String thumbnail = avroModel.getThumbnail();
            String originalImage = avroModel.getOriginalImage();
            String pageUrl = avroModel.getPageUrl();
            page = new WikipediaSolrPage(id, type, lang, title, displayTitle,
                    wikibaseItem, pageId, tid, timestamp, extract, extractHtml,
                    normalizedTitle, thumbnail, originalImage, pageUrl, fullPage);
        }
        return page;
    }

    public WikipediaPageDto
            convertWikiAvroPageToWikiPageDto(WikipediaPageAvroModel avroModel) {
        WikipediaPageDto page = null;
        if (avroModel != null) {
            String id = avroModel.getTid();
            String lang = avroModel.getLang();
            String type = avroModel.getType();
            String title = avroModel.getTitle();
            String displayTitle = avroModel.getDisplayTitle();
            String normalizedTitle = avroModel.getNormalizedTitle();
            Long pageId = avroModel.getPageId();
            String tid = avroModel.getTid();
            String wikibaseItem = avroModel.getWikibaseItem();
            String extract = avroModel.getExtract();
            String extractHtml = avroModel.getExtractHtml();
            String fullPage = null;
            if (kafkaToSolrConfigData.indexCompletePage()) {
                avroModel.getFullPage();
            }
            String timestamp = avroModel.getTimestamp();
            String thumbnail = avroModel.getThumbnail();
            String originalImage = avroModel.getOriginalImage();
            String pageUrl = avroModel.getPageUrl();
            page = new WikipediaPageDto(id, type, lang, title, displayTitle, wikibaseItem,
                    pageId, tid, timestamp, extract, extractHtml, normalizedTitle,
                    thumbnail, originalImage, pageUrl, fullPage);
        }
        return page;
    }

}
