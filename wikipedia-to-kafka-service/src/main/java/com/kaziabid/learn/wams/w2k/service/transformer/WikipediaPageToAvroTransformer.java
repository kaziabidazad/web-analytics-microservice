package com.kaziabid.learn.wams.w2k.service.transformer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kaziabid.learn.wams.common.dto.wiki.WikiFullPage;
import com.kaziabid.learn.wams.common.dto.wiki.WikiPage;
import com.kaziabid.learn.wams.kafka.model.avro.WikipediaPageAvroModel;

/**
 * @author Kazi
 */
@Component
public class WikipediaPageToAvroTransformer {
    @SuppressWarnings("unused")
    private static final Logger LOGGER = LoggerFactory
            .getLogger(WikipediaPageToAvroTransformer.class);

    @SuppressWarnings("unused")
    private ObjectMapper objectMapper;

    public WikipediaPageToAvroTransformer() {
        objectMapper = new ObjectMapper();

    }

    public WikipediaPageAvroModel getWikipediaPageAvroModelFromPage(
            WikiFullPage wikiFullPage) {
        String fullHtmlTextString = wikiFullPage.fullPage();
        WikiPage wikiPage = wikiFullPage.wikiPage();
        WikipediaPageAvroModel wikipediaPageAvroModel = WikipediaPageAvroModel
                .newBuilder()
                .setTid(wikiPage.tid())
                .setDisplayTitle(wikiPage.displayTitle())
                .setExtract(wikiPage.extract())
                .setExtractHtml(wikiPage.extractHtml())
                .setNormalizedTitle(wikiPage.normalizedTitle())
                .setPageId(wikiPage.pageId())
                .setTimestamp(wikiPage.timestamp())
                .setTitle(wikiPage.title())
                .setWikibaseItem(wikiPage.wikibaseItem())
                .setLang(wikiPage.lang())
                .setType(wikiPage.type())
                .setPageUrl(wikiPage.pageUrl())
                .setThumbnail(wikiPage.thumbnail())
                .setOriginalImage(wikiPage.originalImage())
                .setFullPage(fullHtmlTextString)
                .build();
        return wikipediaPageAvroModel;
    }
}
