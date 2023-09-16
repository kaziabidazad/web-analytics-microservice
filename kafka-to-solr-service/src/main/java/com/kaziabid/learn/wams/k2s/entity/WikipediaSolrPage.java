package com.kaziabid.learn.wams.k2s.entity;

import org.apache.solr.client.solrj.beans.Field;

/**
 * @author Kazi
 */
public class WikipediaSolrPage {

    
    @Field
    private String id;
    
    @Field
    private String type;
    @Field
    private String lang;
    @Field
    private String title;
    @Field
    private String displayTitle;
    @Field
    private String wikibaseItem;
    @Field
    private Long pageId;
    @Field
    private String tid;
    @Field
    private String timestamp;
    @Field
    private String extract;
    @Field
    private String extractHtml;
    @Field
    private String normalizedTitle;
    @Field
    private String thumbnail;
    @Field
    private String originalImage;
    @Field
    private String pageUrl;
    @Field
    private String fullPage;

    /**
     * 
     */
    public WikipediaSolrPage() {
    }

    /**
     * @param id
     * @param type
     * @param lang
     * @param title
     * @param displayTitle
     * @param wikibaseItem
     * @param pageId
     * @param tid
     * @param timestamp
     * @param extract
     * @param extractHtml
     * @param normalizedTitle
     * @param thumbnail
     * @param originalImage
     * @param pageUrl
     * @param fullPage
     */
    public WikipediaSolrPage(String id, String type, String lang, String title,
            String displayTitle, String wikibaseItem, Long pageId, String tid,
            String timestamp, String extract, String extractHtml,
            String normalizedTitle, String thumbnail, String originalImage,
            String pageUrl, String fullPage) {
        super();
        this.id = id;
        this.type = type;
        this.lang = lang;
        this.title = title;
        this.displayTitle = displayTitle;
        this.wikibaseItem = wikibaseItem;
        this.pageId = pageId;
        this.tid = tid;
        this.timestamp = timestamp;
        this.extract = extract;
        this.extractHtml = extractHtml;
        this.normalizedTitle = normalizedTitle;
        this.thumbnail = thumbnail;
        this.originalImage = originalImage;
        this.pageUrl = pageUrl;
        this.fullPage = fullPage;
    }


 
    

}
