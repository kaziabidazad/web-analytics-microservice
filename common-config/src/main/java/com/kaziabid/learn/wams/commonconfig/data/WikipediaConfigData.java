package com.kaziabid.learn.wams.commonconfig.data;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author Kazi
 */
@ConfigurationProperties(prefix = "wikipedia-config")
public record WikipediaConfigData(String wikipediadateformat, String wikipediaFeaturedFeedUrl) {

}
