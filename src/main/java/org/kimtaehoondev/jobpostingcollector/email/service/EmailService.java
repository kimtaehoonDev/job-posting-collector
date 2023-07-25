package org.kimtaehoondev.jobpostingcollector.email.service;

import java.util.List;
import org.kimtaehoondev.jobpostingcollector.email.dto.request.EmailRequestDto;
import org.kimtaehoondev.jobpostingcollector.jobposting.dto.response.JobPostingResponseDto;

public interface EmailService {
    Long register(EmailRequestDto dto);

    Long delete(EmailRequestDto dto);

    void sendJobPostings(List<JobPostingResponseDto> jobPostings);

}
