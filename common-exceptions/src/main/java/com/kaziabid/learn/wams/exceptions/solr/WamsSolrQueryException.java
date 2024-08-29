package com.kaziabid.learn.wams.exceptions.solr;

import com.kaziabid.learn.wams.exceptions.WamsException;

/**
 * @author Kazi Abid
 */
public class WamsSolrQueryException extends WamsException {

    private static final long serialVersionUID = -1978611495267795485L;

    public WamsSolrQueryException() {
    }

    /**
     * @param message
     */
    public WamsSolrQueryException(String message) {
        super(message);
    }

    /**
     * @param cause
     */
    public WamsSolrQueryException(Throwable cause) {
        super(cause);
    }

    /**
     * @param message
     * @param cause
     */
    public WamsSolrQueryException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * @param message
     * @param cause
     * @param enableSuppression
     * @param writableStackTrace
     */
    public WamsSolrQueryException(String message, Throwable cause,
            boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
