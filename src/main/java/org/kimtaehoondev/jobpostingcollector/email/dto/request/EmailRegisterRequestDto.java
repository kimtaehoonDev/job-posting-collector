package org.kimtaehoondev.jobpostingcollector.email.dto.request;

import org.kimtaehoondev.jobpostingcollector.email.Email;

public class EmailRegisterRequestDto {
    private String email;

    public Email toEntity() {
        return Email.create(email);
    }
}
