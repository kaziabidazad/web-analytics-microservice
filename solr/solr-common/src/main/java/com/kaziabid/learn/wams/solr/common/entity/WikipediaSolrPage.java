package com.kaziabid.learn.wams.solr.common.entity;

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
    private Long   pageId;
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
            String timestamp, String extract, String extractHtml, String normalizedTitle,
            String thumbnail, String originalImage, String pageUrl, String fullPage) {
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

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * @return the lang
     */
    public String getLang() {
        return lang;
    }

    /**
     * @param lang the lang to set
     */
    public void setLang(String lang) {
        this.lang = lang;
    }

    /**
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title the title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return the displayTitle
     */
    public String getDisplayTitle() {
        return displayTitle;
    }

    /**
     * @param displayTitle the displayTitle to set
     */
    public void setDisplayTitle(String displayTitle) {
        this.displayTitle = displayTitle;
    }

    /**
     * @return the wikibaseItem
     */
    public String getWikibaseItem() {
        return wikibaseItem;
    }

    /**
     * @param wikibaseItem the wikibaseItem to set
     */
    public void setWikibaseItem(String wikibaseItem) {
        this.wikibaseItem = wikibaseItem;
    }

    /**
     * @return the pageId
     */
    public Long getPageId() {
        return pageId;
    }

    /**
     * @param pageId the pageId to set
     */
    public void setPageId(Long pageId) {
        this.pageId = pageId;
    }

    /**
     * @return the tid
     */
    public String getTid() {
        return tid;
    }

    /**
     * @param tid the tid to set
     */
    public void setTid(String tid) {
        this.tid = tid;
    }

    /**
     * @return the timestamp
     */
    public String getTimestamp() {
        return timestamp;
    }

    /**
     * @param timestamp the timestamp to set
     */
    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    /**
     * @return the extract
     */
    public String getExtract() {
        return extract;
    }

    /**
     * @param extract the extract to set
     */
    public void setExtract(String extract) {
        this.extract = extract;
    }

    /**
     * @return the extractHtml
     */
    public String getExtractHtml() {
        return extractHtml;
    }

    /**
     * @param extractHtml the extractHtml to set
     */
    public void setExtractHtml(String extractHtml) {
        this.extractHtml = extractHtml;
    }

    /**
     * @return the normalizedTitle
     */
    public String getNormalizedTitle() {
        return normalizedTitle;
    }

    /**
     * @param normalizedTitle the normalizedTitle to set
     */
    public void setNormalizedTitle(String normalizedTitle) {
        this.normalizedTitle = normalizedTitle;
    }

    /**
     * @return the thumbnail
     */
    public String getThumbnail() {
        return thumbnail;
    }

    /**
     * @param thumbnail the thumbnail to set
     */
    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    /**
     * @return the originalImage
     */
    public String getOriginalImage() {
        return originalImage;
    }

    /**
     * @param originalImage the originalImage to set
     */
    public void setOriginalImage(String originalImage) {
        this.originalImage = originalImage;
    }

    /**
     * @return the pageUrl
     */
    public String getPageUrl() {
        return pageUrl;
    }

    /**
     * @param pageUrl the pageUrl to set
     */
    public void setPageUrl(String pageUrl) {
        this.pageUrl = pageUrl;
    }

    /**
     * @return the fullPage
     */
    public String getFullPage() {
        return fullPage;
    }

    /**
     * @param fullPage the fullPage to set
     */
    public void setFullPage(String fullPage) {
        this.fullPage = fullPage;
    }

    @Override
    public String toString() {
        return "WikipediaSolrPage [id="
                + id + ", type=" + type + ", lang=" + lang + ", title=" + title
                + ", displayTitle=" + displayTitle + ", wikibaseItem=" + wikibaseItem
                + ", pageId=" + pageId + ", tid=" + tid + ", timestamp=" + timestamp
                + ", extract=" + extract + ", extractHtml=" + extractHtml
                + ", normalizedTitle=" + normalizedTitle + ", thumbnail=" + thumbnail
                + ", originalImage=" + originalImage + ", pageUrl=" + pageUrl
                + ", fullPage=" + fullPage + "]";
    }

}
