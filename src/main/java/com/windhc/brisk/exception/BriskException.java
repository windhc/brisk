package com.windhc.brisk.exception;

/**
 * @author windhc
 */
public class BriskException extends RuntimeException {

    public BriskException() {
        super();
    }

    public BriskException(String message) {
        super(message);
    }

    public BriskException(String message, Throwable cause) {
        super(message, cause);
    }

    public BriskException(Throwable cause) {
        super(cause);
    }

    protected BriskException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
