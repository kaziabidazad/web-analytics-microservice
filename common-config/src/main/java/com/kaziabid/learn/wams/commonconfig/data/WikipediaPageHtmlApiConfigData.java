package com.kaziabid.learn.wams.commonconfig.data;

import java.util.List;

/**
 * @author Kazi
 */
public record WikipediaPageHtmlApiConfigData(String baseUrl,
        List<UrlParam> urlParams) {

}
