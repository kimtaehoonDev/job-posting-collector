package org.kimtaehoondev.jobpostingcollector.exception;

import lombok.Getter;

@Getter
public enum ErrorCode {
    HTTP_PARSING(1, "HTTP를 파싱하는 과정에서 문제가 발생했습니다"),
    CONNECT(2,"%s와 연결하는 과정에서 문제가 발생했습니다");

    private final int code;
    private final String message;

    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }
}