package org.kimtaehoondev.jobpostingcollector.exception.impl;

import org.kimtaehoondev.jobpostingcollector.exception.CustomException;
import org.kimtaehoondev.jobpostingcollector.exception.ErrorCode;

public class CodeInvalidException extends CustomException {
    public CodeInvalidException() {
        super(ErrorCode.CODE_INVALID);
    }
}
