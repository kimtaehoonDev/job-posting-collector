package org.kimtaehoondev.jpcollector.exception.impl;

import org.kimtaehoondev.jpcollector.exception.CustomException;
import org.kimtaehoondev.jpcollector.exception.ErrorCode;

public class EmailPasswordInvalidException extends CustomException {
    public EmailPasswordInvalidException() {
        super(ErrorCode.EMAIL_PASSWORD_INVALID);
    }
}
