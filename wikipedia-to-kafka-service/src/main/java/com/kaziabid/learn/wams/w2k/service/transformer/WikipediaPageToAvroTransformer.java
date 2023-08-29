package com.kaziabid.learn.wams.w2k.service.transformer;

import org.springframework.stereotype.Component;

import com.kaziabid.learn.wams.kafka.model.avro.WikipediaPageAvroModel;
import com.kaziabid.learn.wams.w2k.model.WikiPage;

/**
 * @author Kazi
 */
@Component
public class WikipediaPageToAvroTransformer {

    public WikipediaPageAvroModel getWikipediaPageAvroModelFromPage(WikiPage wikiPage) {
        return WikipediaPageAvroModel.newBuilder()
                
                .setTid(wikiPage.tid())
                
                .setDisplayTitle(wikiPage.displayTitle())
                
                .setExtract(wikiPage.extract())
                
                .setNormalizedTitle(wikiPage.normalizedTitle())
                
                .setPageId(wikiPage.pageId())
                
                .setTimestamp(wikiPage.timestamp())
                
                .setTitle(wikiPage.title())
                
                .setWikibaseItem(wikiPage.wikibaseItem())
                
                .build();
    }
}
