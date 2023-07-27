package org.kimtaehoondev.jobpostingcollector.email.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@Getter
@Setter
public class EmailRegisterDto {
    private String email;
    private String code;
    private String pwd;
}
