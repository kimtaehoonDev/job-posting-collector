package org.kimtaehoondev.jpcollector.exception.impl;

import org.kimtaehoondev.jpcollector.exception.CustomException;
import org.kimtaehoondev.jpcollector.exception.ErrorCode;

public class ThreadNotWorkingException extends CustomException {
    public ThreadNotWorkingException() {
        super(ErrorCode.THREAD_NOT_WORKING);
    }
}
