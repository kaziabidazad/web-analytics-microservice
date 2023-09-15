package com.kaziabid.learn.wams.common.dto.wiki;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Kazi
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public record WikiPage(
        @JsonProperty("type") String type,
        @JsonProperty("lang") String lang,
        @JsonProperty("title") String title,
        @JsonProperty("displaytitle") String displayTitle,
        @JsonProperty("wikibase_item") String wikibaseItem,
        @JsonProperty("pageid") Long pageId,
        @JsonProperty("tid") String tid,
        @JsonProperty("timestamp") String timestamp,
        @JsonProperty("extract") String extract,
        @JsonProperty("extract_html") String extractHtml,
        @JsonProperty("normalizedtitle") String normalizedTitle,
        @JsonProperty("thumbnail") String thumbnail,
        @JsonProperty("originalimage") String originalImage,
        String pageUrl)

{

}
