package com.kaziabid.learn.wams.exceptions;

/**
 * @author Kazi Abid
 */
public class WamsException extends Exception {

    private static final long serialVersionUID = -1401081242195601318L;

    public WamsException() {
    }

    /**
     * @param message
     */
    public WamsException(String message) {
        super(message);
    }

    /**
     * @param cause
     */
    public WamsException(Throwable cause) {
        super(cause);
    }

    /**
     * @param message
     * @param cause
     */
    public WamsException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * @param message
     * @param cause
     * @param enableSuppression
     * @param writableStackTrace
     */
    public WamsException(String message, Throwable cause, boolean enableSuppression,
            boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
