package com.kaziabid.learn.wams.solr.query.webclient.dto;

import com.kaziabid.learn.wams.common.dto.CommonDTO;

/**
 * @author Kazi Abid
 */
public record SolrServiceQueryResponseWikiData(
        String id, String type, String lang, String title, String displayTitle,
        String wikibaseItem, Long pageId, String tid, String timestamp, String extract,
        String extractHtml, String normalizedTitle, String thumbnail,
        String originalImage, String pageUrl, String fullPage) implements CommonDTO {

}
