package com.kaziabid.learn.wams.w2k.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Kazi
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public record WikiPage(@JsonProperty("type") String type

        , @JsonProperty("title") String title

        , @JsonProperty("displaytitle") String displayTitle

        , @JsonProperty("wikibase_item") String wikibaseItem

        , @JsonProperty("pageid") String pageId

        , @JsonProperty("tid") String tid

        , @JsonProperty("timestamp") String timestamp

        , @JsonProperty("extract") String extract

        , @JsonProperty("normalizedtitle") String normalizedTitle) {

}
