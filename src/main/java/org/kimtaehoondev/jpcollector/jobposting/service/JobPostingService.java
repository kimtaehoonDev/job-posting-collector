package org.kimtaehoondev.jpcollector.jobposting.service;

import java.util.List;
import org.kimtaehoondev.jpcollector.jobposting.dto.response.JobPostingResponseDto;
import org.kimtaehoondev.jpcollector.jobposting.dto.response.JobPostingCrawlingResult;

public interface JobPostingService {
    List<JobPostingResponseDto> findAll();

    void updateAll(List<JobPostingCrawlingResult> crawlingResults);

    List<JobPostingResponseDto> getNewlyJobPosting();
}
