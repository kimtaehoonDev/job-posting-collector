package org.kimtaehoondev.jobpostingcollector.jobposting.service;

import java.util.List;
import org.kimtaehoondev.jobpostingcollector.dto.JobPostingResponseDto;
import org.kimtaehoondev.jobpostingcollector.jobposting.dto.JobPostingData;

public interface JobPostingService {
    List<JobPostingResponseDto> findAll();

    void updateAll(List<JobPostingData> jobPostings);
}
