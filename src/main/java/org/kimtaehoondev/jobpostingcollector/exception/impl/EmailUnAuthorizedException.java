package org.kimtaehoondev.jobpostingcollector.exception.impl;

import org.kimtaehoondev.jobpostingcollector.exception.CustomException;
import org.kimtaehoondev.jobpostingcollector.exception.ErrorCode;

public class EmailUnAuthorizedException extends CustomException {
    public EmailUnAuthorizedException() {
        super(ErrorCode.EMAIL_UNAUTHORIZED);
    }
}
