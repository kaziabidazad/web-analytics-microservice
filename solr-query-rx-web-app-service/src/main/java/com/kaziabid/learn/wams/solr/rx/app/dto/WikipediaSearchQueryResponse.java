package com.kaziabid.learn.wams.solr.rx.app.dto;

import com.kaziabid.learn.wams.common.dto.CommonDTO;

/**
 * @author Kazi Abid
 */
public record WikipediaSearchQueryResponse(
        String pageId, String pageTitle, String pageContent, String imageUrl,
        String pageLinkUrl, String timestamp) implements CommonDTO {
}
