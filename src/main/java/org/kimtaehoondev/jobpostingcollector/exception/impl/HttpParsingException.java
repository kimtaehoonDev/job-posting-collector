package org.kimtaehoondev.jobpostingcollector.exception.impl;

import org.kimtaehoondev.jobpostingcollector.exception.CustomException;
import org.kimtaehoondev.jobpostingcollector.exception.ErrorCode;

public class HttpParsingException extends CustomException {
    public HttpParsingException() {
        super(ErrorCode.HTTP_PARSING);
    }
}
