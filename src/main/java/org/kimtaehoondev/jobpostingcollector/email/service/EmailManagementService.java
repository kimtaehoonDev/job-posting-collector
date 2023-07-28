package org.kimtaehoondev.jobpostingcollector.email.service;

import org.kimtaehoondev.jobpostingcollector.web.dto.request.EmailDeleteDto;
import org.kimtaehoondev.jobpostingcollector.web.dto.request.EmailRegisterDto;

public interface EmailManagementService {
    Long register(EmailRegisterDto dto);

    Long delete(EmailDeleteDto dto);

    void verifyAuthCode(String email, String code);

    void sendAuthCode(String email, String code);
}
