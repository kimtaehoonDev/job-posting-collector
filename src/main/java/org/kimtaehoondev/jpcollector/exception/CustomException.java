package org.kimtaehoondev.jpcollector.exception;

public class CustomException extends RuntimeException {
    private final ErrorCode code;

    public CustomException(ErrorCode code) {
        super(code.getMessage());
        this.code = code;
    }

    public CustomException(ErrorCode code, Object... params) {
        super(String.format(code.getMessage(), params));
        this.code = code;
    }
}
