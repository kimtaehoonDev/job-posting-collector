package org.kimtaehoondev.jobpostingcollector.email.dto.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@Getter
@Setter
public class EmailRegisterDto {
    @NotEmpty(message = "이메일은 필수로 입력되어야 합니다")
    @Email(message = "이메일의 양식이 잘못되었습니다")
    private String email;

    @NotEmpty(message = "코드는 필수로 입력되어야 합니다")
    private String code;

    @NotEmpty(message = "비밀번호는 필수로 입력되어야 합니다")
    private String pwd;
}
