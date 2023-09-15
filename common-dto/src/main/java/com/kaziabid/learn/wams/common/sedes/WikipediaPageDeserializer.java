package com.kaziabid.learn.wams.common.sedes;

import java.io.IOException;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.kaziabid.learn.wams.common.dto.wiki.WikiPage;

/**
 * @author Kazi
 */
public class WikipediaPageDeserializer extends StdDeserializer<WikiPage> {

    private static final long serialVersionUID = -3328094960090589365L;

    /**
     * @param vc
     */
    public WikipediaPageDeserializer(Class<?> vc) {
        super(vc);
    }

    public WikipediaPageDeserializer() {
        this(null);
    }

    @Override
    public WikiPage deserialize(JsonParser p, DeserializationContext ctxt)
            throws IOException, JacksonException {
        JsonNode wikiNode = p.getCodec().readTree(p);
        String type = wikiNode.get("type") != null
                ? wikiNode.get("type").textValue()
                : null;

        String lang = wikiNode.get("lang") != null
                ? wikiNode.get("lang").textValue()
                : null;

        String title = wikiNode.get("title") != null
                ? wikiNode.get("title").textValue()
                : null;

        String displayTitle = wikiNode.get("displaytitle") != null
                ? wikiNode.get("displaytitle").textValue()
                : null;

        String wikibaseItem = wikiNode.get("wikibase_item") != null
                ? wikiNode.get("wikibase_item").textValue()
                : null;

        Long pageId = wikiNode.get("pageid") != null
                ? wikiNode.get("pageid").longValue()
                : null;

        String tid = wikiNode.get("tid") != null
                ? wikiNode.get("tid").textValue()
                : null;

        String timestamp = wikiNode.get("timestamp") != null
                ? wikiNode.get("timestamp").textValue()
                : null;

        String extract = wikiNode.get("extract") != null
                ? wikiNode.get("extract").textValue()
                : null;

        String extractHtml = wikiNode.get("extract_html") != null
                ? wikiNode.get("extract").textValue()
                : null;

        String normalizedTitle = wikiNode.get("normalizedtitle") != null
                ? wikiNode.get("normalizedtitle").textValue()
                : null;

        JsonNode thumbnailImageNode = wikiNode.get("thumbnail") != null
                ? wikiNode.get("thumbnail").get("source")
                : null;
        String thumbnail = thumbnailImageNode != null
                ? thumbnailImageNode.textValue()
                : null;

        JsonNode originalImageNode = wikiNode.get("originalimage") != null
                ? wikiNode.get("originalimage").get("source")
                : null;
        String originalImage = originalImageNode != null
                ? originalImageNode.textValue()
                : null;

        JsonNode contentUrlNode = wikiNode.get("content_urls");
        JsonNode contentUrlDesktopNode = contentUrlNode != null
                ? contentUrlNode.get("desktop")
                : null;
        JsonNode pageUrlDesktopNode = contentUrlDesktopNode != null
                ? contentUrlDesktopNode.get("page")
                : null;

        String pageUrl = pageUrlDesktopNode != null
                ? pageUrlDesktopNode.textValue()
                : null;
        WikiPage wikiPage = new WikiPage(type, lang, title, displayTitle,
                wikibaseItem,
                pageId, tid, timestamp, extract, extractHtml, normalizedTitle,
                thumbnail, originalImage, pageUrl);
        return wikiPage;
    }

}
