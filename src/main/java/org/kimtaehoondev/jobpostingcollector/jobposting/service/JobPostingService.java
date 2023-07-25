package org.kimtaehoondev.jobpostingcollector.jobposting.service;

import java.util.List;
import org.kimtaehoondev.jobpostingcollector.dto.JobPostingResponseDto;
import org.kimtaehoondev.jobpostingcollector.jobposting.JobPosting;

public interface JobPostingService {
    List<JobPostingResponseDto> findAll();

    void updateAll(List<JobPosting> jobPostings);
}
