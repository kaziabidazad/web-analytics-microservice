package com.kaziabid.learn.wams.kafkaadmin.exception;

/**
 * @author Kazi
 */
public class KafkaClientException extends RuntimeException {

    private static final long serialVersionUID = -1084334205235373651L;

    public KafkaClientException() {
    }

    /**
     * @param message
     * @param cause
     * @param enableSuppression
     * @param writableStackTrace
     */
    public KafkaClientException(String message, Throwable cause, boolean enableSuppression,
            boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    /**
     * @param message
     * @param cause
     */
    public KafkaClientException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * @param message
     */
    public KafkaClientException(String message) {
        super(message);
    }

    /**
     * @param cause
     */
    public KafkaClientException(Throwable cause) {
        super(cause);
    }

}
