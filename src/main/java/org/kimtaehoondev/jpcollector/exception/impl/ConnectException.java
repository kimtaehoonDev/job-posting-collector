package org.kimtaehoondev.jpcollector.exception.impl;

import org.kimtaehoondev.jpcollector.exception.CustomException;
import org.kimtaehoondev.jpcollector.exception.ErrorCode;

public class ConnectException extends CustomException {
    public ConnectException(String url) {
        super(ErrorCode.CONNECT, url);
    }
}
