package com.kaziabid.learn.wams.w2k.service.transformer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kaziabid.learn.wams.common.dto.wiki.WikiPage;
import com.kaziabid.learn.wams.kafka.model.avro.WikipediaPageAvroModel;

/**
 * @author Kazi
 */
@Component
public class WikipediaPageToAvroTransformer {
    private static final Logger LOGGER = LoggerFactory
            .getLogger(WikipediaPageToAvroTransformer.class);

    private ObjectMapper objectMapper;

    public WikipediaPageToAvroTransformer() {
        objectMapper = new ObjectMapper();

    }

    public WikipediaPageAvroModel getWikipediaPageAvroModelFromPage(
            WikiPage wikiPage) {
        if (wikiPage.thumbnail() == null) {
            try {
                LOGGER.error("##### Empty thumbnail:  {}"
                        + objectMapper.writerWithDefaultPrettyPrinter()
                                .writeValueAsString(wikiPage));
            } catch (JsonProcessingException e) {
                LOGGER.error(
                        "#### Empty thumnail(Error pretty printing ) : {} ",
                        wikiPage, e);
            }
        }
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
                .build();
        return wikipediaPageAvroModel;
    }
}
