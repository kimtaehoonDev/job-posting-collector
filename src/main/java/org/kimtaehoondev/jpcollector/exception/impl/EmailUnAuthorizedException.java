package org.kimtaehoondev.jpcollector.exception.impl;

import org.kimtaehoondev.jpcollector.exception.CustomException;
import org.kimtaehoondev.jpcollector.exception.ErrorCode;

public class EmailUnAuthorizedException extends CustomException {
    public EmailUnAuthorizedException() {
        super(ErrorCode.EMAIL_UNAUTHORIZED);
    }
}
