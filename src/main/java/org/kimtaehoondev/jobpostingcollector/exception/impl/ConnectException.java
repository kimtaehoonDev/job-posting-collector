package org.kimtaehoondev.jobpostingcollector.exception.impl;

import org.kimtaehoondev.jobpostingcollector.exception.CustomException;
import org.kimtaehoondev.jobpostingcollector.exception.ErrorCode;

public class ConnectException extends CustomException {
    public ConnectException(String url) {
        super(ErrorCode.CONNECT, url);
    }
}
