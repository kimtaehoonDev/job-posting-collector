package org.kimtaehoondev.jpcollector.exception.impl;

import org.kimtaehoondev.jpcollector.exception.CustomException;
import org.kimtaehoondev.jpcollector.exception.ErrorCode;

public class EmailDuplicateException extends CustomException {
    public EmailDuplicateException() {
        super(ErrorCode.EMAIL_DUPLICATE);
    }
}
