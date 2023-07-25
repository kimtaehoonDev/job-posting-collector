package org.kimtaehoondev.jobpostingcollector.email.dto.request;

import org.kimtaehoondev.jobpostingcollector.email.Email;

public class EmailRequestDto {
    private String email;
    private String pwd;

    public Email toEntity() {
        return Email.create(email);
    }

}
