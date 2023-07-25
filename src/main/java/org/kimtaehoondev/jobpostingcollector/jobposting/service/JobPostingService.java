package org.kimtaehoondev.jobpostingcollector.jobposting.service;

import java.util.List;
import org.kimtaehoondev.jobpostingcollector.jobposting.dto.response.JobPostingResponseDto;
import org.kimtaehoondev.jobpostingcollector.jobposting.dto.response.JobPostingCrawlingResult;

public interface JobPostingService {
    List<JobPostingResponseDto> findAll();

    void updateAll(List<JobPostingCrawlingResult> crawlingResults);

    List<JobPostingResponseDto> getNewlyJobPosting();
}
