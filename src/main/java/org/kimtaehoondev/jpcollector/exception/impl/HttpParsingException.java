package org.kimtaehoondev.jpcollector.exception.impl;

import org.kimtaehoondev.jpcollector.exception.CustomException;
import org.kimtaehoondev.jpcollector.exception.ErrorCode;

public class HttpParsingException extends CustomException {
    public HttpParsingException() {
        super(ErrorCode.HTTP_PARSING);
    }
}
