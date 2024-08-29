package com.kaziabid.learn.wams.exceptions;

/**
 * @author Kazi Abid
 */
public class WamsServiceInitializationException extends WamsRuntimeException {

    private static final long serialVersionUID = 8905342696498911422L;

    public WamsServiceInitializationException() {
    }

    /**
     * @param message
     */
    public WamsServiceInitializationException(String message) {
        super(message);
    }

    /**
     * @param cause
     */
    public WamsServiceInitializationException(Throwable cause) {
        super(cause);
    }

    /**
     * @param message
     * @param cause
     */
    public WamsServiceInitializationException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * @param message
     * @param cause
     * @param enableSuppression
     * @param writableStackTrace
     */
    public WamsServiceInitializationException(String message, Throwable cause,
            boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
