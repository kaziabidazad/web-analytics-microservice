package com.kaziabid.learn.wams.k2s.converter;

import org.springframework.stereotype.Component;

import com.kaziabid.learn.wams.k2s.entity.WikipediaSolrPage;
import com.kaziabid.learn.wams.kafka.model.avro.WikipediaPageAvroModel;

/**
 * @author Kazi
 */
@Component
public class EntityConverter {

    public WikipediaSolrPage convertWikiAvroPageToWikiSolrPage(
            WikipediaPageAvroModel avroModel) {
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
            String fullPage = avroModel.getFullPage();
            String timestamp = avroModel.getTimestamp();
            String thumbnail = avroModel.getThumbnail();
            String originalImage = avroModel.getOriginalImage();
            String pageUrl = avroModel.getPageUrl();
            page = new WikipediaSolrPage(id, type, lang, title, displayTitle,
                    wikibaseItem, pageId, tid, timestamp, extract, extractHtml,
                    normalizedTitle, thumbnail, originalImage, pageUrl,
                    fullPage);
        }
        return page;
    }

}
