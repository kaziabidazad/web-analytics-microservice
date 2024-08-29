package com.kaziabid.learn.wams.exceptions;

/**
 * The parent WAMS Runtime Exception
 * 
 * @author Kazi Abid
 */
public class WamsRuntimeException extends RuntimeException {

    private static final long serialVersionUID = -1475633423971211843L;

    public WamsRuntimeException() {
    }

    /**
     * @param message
     */
    public WamsRuntimeException(String message) {
        super(message);
    }

    /**
     * @param cause
     */
    public WamsRuntimeException(Throwable cause) {
        super(cause);
    }

    /**
     * @param message
     * @param cause
     */
    public WamsRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * @param message
     * @param cause
     * @param enableSuppression
     * @param writableStackTrace
     */
    public WamsRuntimeException(String message, Throwable cause,
            boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
