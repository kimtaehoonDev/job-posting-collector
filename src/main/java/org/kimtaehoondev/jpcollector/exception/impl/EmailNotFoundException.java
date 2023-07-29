package org.kimtaehoondev.jpcollector.exception.impl;

import org.kimtaehoondev.jpcollector.exception.CustomException;
import org.kimtaehoondev.jpcollector.exception.ErrorCode;

public class EmailNotFoundException extends CustomException {
    public EmailNotFoundException() {
        super(ErrorCode.EMAIL_NOT_FOUND);
    }
}
