package com.kaziabid.learn.wams.kafka.model.avro;

import org.springframework.stereotype.Component;

import com.kaziabid.learn.wams.common.dto.wiki.WikiPage;

/**
 * @author Kazi
 */
@Component
public class DTOConverter {

    /**
     * Returns an instance of {@link WikiPage} with the data extracted from the
     * {@link WikipediaPageAvroModel} object.
     * 
     * @param avroModel
     * @return
     */
    public WikiPage convertWikiAvroPageToWikiPage(
            WikipediaPageAvroModel avroModel) {
        WikiPage page = null;
        if (avroModel != null) {
            String lang = avroModel.getLang();
            String type = avroModel.getType();
            String title = avroModel.getTitle();
            String displayTitle = avroModel.getDisplayTitle();
            String normalizedTitle = avroModel.getNormalizedTitle();
            Long pageId = avroModel.getPageId();
            String tid = avroModel.getTid();
            String wikibaseItem = avroModel.getWikibaseItem();
            String extract = avroModel.getExtract();
            String extractHtml = avroModel.getExtract();
            String timestamp = avroModel.getTimestamp();
            String thumbnail = avroModel.getThumbnail();
            String originalImage = avroModel.getOriginalImage();
            String pageUrl = avroModel.getPageUrl();
            page = new WikiPage(type, lang, title, displayTitle, wikibaseItem,
                    pageId, tid, timestamp, extract, extractHtml,
                    normalizedTitle,
                    thumbnail, originalImage, pageUrl);
        }
        return page;
    }
}
