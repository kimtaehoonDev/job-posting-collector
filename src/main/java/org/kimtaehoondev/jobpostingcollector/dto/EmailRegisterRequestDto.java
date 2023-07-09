package org.kimtaehoondev.jobpostingcollector.dto;

import org.kimtaehoondev.jobpostingcollector.entity.Email;

public class EmailRegisterRequestDto {
    private String email;

    public Email toEntity() {
        return Email.create(email);
    }
}
