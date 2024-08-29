package com.kaziabid.learn.wams.exceptions.api;

import com.kaziabid.learn.wams.exceptions.WamsRuntimeException;

/**
 * @author Kazi Abid
 */
public class WamsRestServiceException extends WamsRuntimeException {

    private static final long serialVersionUID = -461280497510154279L;

    private final int httpCode;

    /**
     * @param message
     */
    public WamsRestServiceException(int httpCode, String message) {
        super(message);
        this.httpCode = httpCode;
    }

    /**
     * @param cause
     */
    public WamsRestServiceException(int httpCode, Throwable cause) {
        super(cause);
        this.httpCode = httpCode;
    }

    /**
     * @param message
     * @param cause
     */
    public WamsRestServiceException(int httpCode, String message, Throwable cause) {
        super(message, cause);
        this.httpCode = httpCode;
    }

    /**
     * @param message
     * @param cause
     * @param enableSuppression
     * @param writableStackTrace
     */
    public WamsRestServiceException(int httpCode, String message, Throwable cause,
            boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.httpCode = httpCode;
    }

    /**
     * @return the httpCode
     */
    public int getHttpCode() {
        return httpCode;
    }

    @Override
    public String toString() {
        return "WamsRestServiceException [httpCode="
                + httpCode + ", parent=" + super.toString() + "]";
    }

}
