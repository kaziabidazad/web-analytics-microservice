package com.kaziabid.learn.wams.w2k.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Kazi
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public record WikiFeedResult(@JsonProperty("tfa") WikiPage todaysFetauredArticle,
        @JsonProperty("mostread") MostRead mostRead, @JsonProperty("onthisday") List<OnThisDay> onThisDayArticles) {

}
