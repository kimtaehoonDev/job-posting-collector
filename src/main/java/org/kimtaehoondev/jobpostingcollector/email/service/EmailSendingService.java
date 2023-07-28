package org.kimtaehoondev.jobpostingcollector.email.service;

import java.util.List;
import org.kimtaehoondev.jobpostingcollector.jobposting.dto.response.JobPostingResponseDto;

public interface EmailSendingService {
    void sendJobPostings(List<JobPostingResponseDto> jobPostings);

    void sendAuthCode(String email, String authCode);
}
