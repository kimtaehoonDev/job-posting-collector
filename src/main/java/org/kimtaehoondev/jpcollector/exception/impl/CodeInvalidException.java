package org.kimtaehoondev.jpcollector.exception.impl;

import org.kimtaehoondev.jpcollector.exception.CustomException;
import org.kimtaehoondev.jpcollector.exception.ErrorCode;

public class CodeInvalidException extends CustomException {
    public CodeInvalidException() {
        super(ErrorCode.CODE_INVALID);
    }
}
