package com.kaziabid.learn.wams.solr.client.utils;

import java.util.ArrayList;
import java.util.List;

import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.stereotype.Component;

import com.kaziabid.learn.wams.common.dto.solr.WikipediaPageDto;
import com.kaziabid.learn.wams.solr.common.entity.WikipediaSolrPage;

/**
 * @author Kazi Abid
 */
@Component
public class SolrDTOUtils {

    public List<WikipediaSolrPage>
            convertSolrDocsToWikiSolrPages(SolrDocumentList solrDocumentList) {
        List<WikipediaSolrPage> result = new ArrayList<>();
        if (solrDocumentList != null)
            result = solrDocumentList
                    .stream().map(this::convertSolrDocToWikiSolrpage).toList();
        return result;
    }

    public WikipediaSolrPage convertSolrDocToWikiSolrpage(SolrDocument solrDocument) {
        WikipediaSolrPage page = null;
        if (solrDocument != null) {
            String id = (String) solrDocument.get("id");
            String type = (String) solrDocument.get("type");
            String lang = (String) solrDocument.get("lang");
            String title = (String) solrDocument.get("title");
            String displayTitle = (String) solrDocument.get("displayTitle");
            String wikibaseItem = (String) solrDocument.get("wikibaseItem");
            Long pageId = (Long) solrDocument.get("pageId");
            String tid = (String) solrDocument.get("tid");
            String timestamp = (String) solrDocument.get("timestamp");
            String extract = (String) solrDocument.get("extract");
            String extractHtml = (String) solrDocument.get("extractHtml");
            String normalizedTitle = (String) solrDocument.get("normalizedTitle");
            String thumbnail = (String) solrDocument.get("thumbnail");
            String originalImage = (String) solrDocument.get("originalImage");
            String pageUrl = (String) solrDocument.get("pageUrl");
            String fullPage = (String) solrDocument.get("fullPage");

            page = new WikipediaSolrPage(id, type, lang, title, displayTitle,
                    wikibaseItem, pageId, tid, timestamp, extract, extractHtml,
                    normalizedTitle, thumbnail, originalImage, pageUrl, fullPage);
        }
        return page;
    }

    public List<WikipediaPageDto>
            convertWikiSolrPagesToDTOs(List<WikipediaSolrPage> solrPages) {
        List<WikipediaPageDto> pageDtos = null;
        if (solrPages != null)
            pageDtos = solrPages.stream().map(this::convertWikiSolrPageToDTO).toList();
        return pageDtos;
    }

    public WikipediaPageDto convertWikiSolrPageToDTO(WikipediaSolrPage solrPage) {
        WikipediaPageDto wikipediaPageDto = null;
        if (solrPage != null) {
            String id = solrPage.getId();
            String type = solrPage.getType();
            String lang = solrPage.getLang();
            String title = solrPage.getTitle();
            String displayTitle = solrPage.getDisplayTitle();
            String wikibaseItem = solrPage.getWikibaseItem();
            Long pageId = solrPage.getPageId();
            String tid = solrPage.getTid();
            String timestamp = solrPage.getTimestamp();
            String extract = solrPage.getExtract();
            String extractHtml = solrPage.getExtractHtml();
            String normalizedTitle = solrPage.getNormalizedTitle();
            String thumbnail = solrPage.getThumbnail();
            String originalImage = solrPage.getOriginalImage();
            String pageUrl = solrPage.getPageUrl();
            String fullPage = solrPage.getFullPage();
            wikipediaPageDto = new WikipediaPageDto(id, type, lang, title, displayTitle,
                    wikibaseItem, pageId, tid, timestamp, extract, extractHtml,
                    normalizedTitle, thumbnail, originalImage, pageUrl, fullPage);
        }
        return wikipediaPageDto;
    }

    public List<WikipediaSolrPage>
            convertDtoToWikipediaSolrPage(List<WikipediaPageDto> dtos) {
        List<WikipediaSolrPage> pages = null;
        if (dtos != null) {
            pages = dtos
                    .stream().filter(d -> d != null)
                    .map(this::convertDtoToWikipediaSolrPage).toList();
        }
        return pages;
    }

    public WikipediaSolrPage convertDtoToWikipediaSolrPage(WikipediaPageDto dto) {
        WikipediaSolrPage page = null;
        if (dto != null) {
            String id = dto.id();
            String type = dto.type();
            String lang = dto.lang();
            String title = dto.title();
            String displayTitle = dto.displayTitle();
            String wikibaseItem = dto.wikibaseItem();
            Long pageId = dto.pageId();
            String tid = dto.tid();
            String timestamp = dto.timestamp();
            String extract = dto.extract();
            String extractHtml = dto.extractHtml();
            String normalizedTitle = dto.normalizedTitle();
            String thumbnail = dto.thumbnail();
            String originalImage = dto.originalImage();
            String pageUrl = dto.pageUrl();
            String fullPage = dto.fullPage();
            page = new WikipediaSolrPage(id, type, lang, title, displayTitle,
                    wikibaseItem, pageId, tid, timestamp, extract, extractHtml,
                    normalizedTitle, thumbnail, originalImage, pageUrl, fullPage);
        }
        return page;
    }
}
