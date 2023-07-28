package org.kimtaehoondev.jobpostingcollector.web.dto.request;

import javax.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@Getter
@Setter
public class SendAuthCodeDto {
    @NotEmpty(message = "이메일은 필수로 입력되어야 합니다")
    private String email;
}
