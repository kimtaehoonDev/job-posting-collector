package org.kimtaehoondev.jobpostingcollector.email.service;

import java.util.List;
import org.kimtaehoondev.jobpostingcollector.email.dto.request.EmailDeleteDto;
import org.kimtaehoondev.jobpostingcollector.email.dto.request.EmailRegisterDto;
import org.kimtaehoondev.jobpostingcollector.jobposting.dto.response.JobPostingResponseDto;

public interface EmailService {
    Long register(EmailRegisterDto dto);

    Long delete(EmailDeleteDto dto);

    void sendJobPostings(List<JobPostingResponseDto> jobPostings);

    void sendAuthCode(String email);

    void verifyAuthCode(String email, String code);
}
