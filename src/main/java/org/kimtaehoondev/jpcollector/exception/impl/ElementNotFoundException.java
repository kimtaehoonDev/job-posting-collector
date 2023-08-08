package org.kimtaehoondev.jpcollector.exception.impl;

import org.kimtaehoondev.jpcollector.exception.CustomException;
import org.kimtaehoondev.jpcollector.exception.ErrorCode;

public class ElementNotFoundException extends CustomException {
    public ElementNotFoundException() {
        super(ErrorCode.ELEMENT_NOT_FOUND);
    }
    //TODO

}
