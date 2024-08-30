package com.kaziabid.learn.wams.solr.query.webclient.dto;

import java.util.List;
import java.util.Objects;

import org.springframework.stereotype.Component;

import com.kaziabid.learn.wams.common.dto.solr.WikipediaPageDto;

/**
 * @author Kazi Abid
 */
@Component
public class DtoConverter {

    public List<WikipediaSearchQueryResponse>
            convertWikiPagesToSearchQueryResponse(List<WikipediaPageDto> inList) {
        if (inList == null)
            return null;
        return inList
                .stream().filter(Objects::nonNull)
                .map(this::convertWikiPageDtoToSearchQueryResponse).toList();
    }

    public WikipediaSearchQueryResponse
            convertWikiPageDtoToSearchQueryResponse(WikipediaPageDto in) {
        WikipediaSearchQueryResponse out = null;
        if (in != null) {

            String pageId = in.id();
            String pageTitle = in.normalizedTitle();
            String pageContent = in.extract();
            String imageUrl = in.thumbnail();
            String pageLinkUrl = in.pageUrl();
            String timestmp = in.timestamp();
            out = new WikipediaSearchQueryResponse(pageId, pageTitle, pageContent,
                    imageUrl, pageLinkUrl, timestmp);
        }
        return out;
    }
}
