package org.kimtaehoondev.jpcollector.email.service;

import org.kimtaehoondev.jpcollector.web.dto.request.EmailDeleteDto;
import org.kimtaehoondev.jpcollector.web.dto.request.EmailRegisterDto;

public interface EmailManagementService {
    Long register(EmailRegisterDto dto);

    Long delete(EmailDeleteDto dto);

    void verifyAuthCode(String email, String code);

    void sendAuthCode(String email, String code);
}
