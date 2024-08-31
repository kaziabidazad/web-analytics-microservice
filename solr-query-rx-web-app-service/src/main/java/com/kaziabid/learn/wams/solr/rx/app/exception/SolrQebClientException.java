package com.kaziabid.learn.wams.solr.rx.app.exception;

/**
 * @author Kazi Abid
 */
public class SolrQebClientException extends RuntimeException {

    private static final long serialVersionUID = -2505008784444706122L;

    public SolrQebClientException() {
    }

    /**
     * @param message
     */
    public SolrQebClientException(String message) {
        super(message);
    }

    /**
     * @param cause
     */
    public SolrQebClientException(Throwable cause) {
        super(cause);
    }

    /**
     * @param message
     * @param cause
     */
    public SolrQebClientException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * @param message
     * @param cause
     * @param enableSuppression
     * @param writableStackTrace
     */
    public SolrQebClientException(String message, Throwable cause,
            boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
