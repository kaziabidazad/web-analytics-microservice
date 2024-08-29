package com.kaziabid.learn.wams.common.dto.solr;

import com.kaziabid.learn.wams.common.dto.CommonDTO;

/**
 * @author Kazi Abid
 */
public record WikipediaPageDto(
        String id, String type, String lang, String title, String displayTitle,
        String wikibaseItem, Long pageId, String tid, String timestamp, String extract,
        String extractHtml, String normalizedTitle, String thumbnail,
        String originalImage, String pageUrl, String fullPage) implements CommonDTO {

    /**
     * Returns a {@link WikipediaPageDto} object with all the fields set to empty
     * strings and pageId set to -1
     * 
     */
    public static WikipediaPageDto build() {
        return new WikipediaPageDto("", "", "", "", "", "", -1l, "", "", "", "", "", "",
                "", "", "");
    }

}
