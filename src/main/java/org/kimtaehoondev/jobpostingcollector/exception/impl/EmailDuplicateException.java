package org.kimtaehoondev.jobpostingcollector.exception.impl;

import org.kimtaehoondev.jobpostingcollector.exception.CustomException;
import org.kimtaehoondev.jobpostingcollector.exception.ErrorCode;

public class EmailDuplicateException extends CustomException {
    public EmailDuplicateException() {
        super(ErrorCode.EMAIL_DUPLICATE);
    }
}
