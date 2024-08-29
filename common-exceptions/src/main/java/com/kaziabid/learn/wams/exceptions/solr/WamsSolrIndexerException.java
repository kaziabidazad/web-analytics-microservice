package com.kaziabid.learn.wams.exceptions.solr;

import com.kaziabid.learn.wams.exceptions.WamsException;

/**
 * @author Kazi Abid
 */
public class WamsSolrIndexerException extends WamsException {

    private static final long serialVersionUID = 503717893508995574L;

    /**
     * 
     */
    public WamsSolrIndexerException() {
    }

    /**
     * @param message
     */
    public WamsSolrIndexerException(String message) {
        super(message);
    }

    /**
     * @param cause
     */
    public WamsSolrIndexerException(Throwable cause) {
        super(cause);
    }

    /**
     * @param message
     * @param cause
     */
    public WamsSolrIndexerException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * @param message
     * @param cause
     * @param enableSuppression
     * @param writableStackTrace
     */
    public WamsSolrIndexerException(String message, Throwable cause,
            boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
