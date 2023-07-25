package org.kimtaehoondev.jobpostingcollector.email.service;

import java.util.List;
import org.kimtaehoondev.jobpostingcollector.email.dto.request.EmailRegisterRequestDto;
import org.kimtaehoondev.jobpostingcollector.jobposting.JobPosting;

public interface EmailService {
    Long registerEmail(EmailRegisterRequestDto dto);

    void sendJobPostingUpdateToAll(List<JobPosting> jobPostings);

}
