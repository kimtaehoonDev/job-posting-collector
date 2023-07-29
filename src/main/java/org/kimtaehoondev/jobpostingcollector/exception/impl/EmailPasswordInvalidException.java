package org.kimtaehoondev.jobpostingcollector.exception.impl;

import org.kimtaehoondev.jobpostingcollector.exception.CustomException;
import org.kimtaehoondev.jobpostingcollector.exception.ErrorCode;

public class EmailPasswordInvalidException extends CustomException {
    public EmailPasswordInvalidException() {
        super(ErrorCode.EMAIL_PASSWORD_INVALID);
    }
}
