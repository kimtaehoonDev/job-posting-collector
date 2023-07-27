package org.kimtaehoondev.jobpostingcollector.email.dto.request;

import lombok.Data;

@Data
public class EmailRequestDto {
    private String email;

    private String code;

    private String pwd;
}
