package org.kimtaehoondev.jpcollector.exception;

import lombok.Getter;

@Getter
public enum ErrorCode {
    HTTP_PARSING(1, "HTTP를 파싱하는 과정에서 문제가 발생했습니다"),
    CONNECT(2,"%s와 연결하는 과정에서 문제가 발생했습니다"),
    EMAIL_DUPLICATE(3,"해당 이메일은 이미 등록되어 있습니다"),
    EMAIL_UNAUTHORIZED(4,"인증되지 않았거나, 만료된 이메일입니다"),
    EMAIL_NOT_FOUND(5, "이메일을 찾을 수 없습니다"),
    EMAIL_PASSWORD_INVALID(6,"이메일 비밀번호가 일치하지 않습니다"),
    CODE_INVALID(7,"보내준 코드가 만료되었거나, 일치하지 않습니다"),
    ELEMENT_NOT_FOUND(8, "해당되는 HTML 요소를 찾지 못했습니다");

    private final int code;
    private final String message;

    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }
}