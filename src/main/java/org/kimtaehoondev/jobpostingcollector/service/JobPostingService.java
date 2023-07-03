package org.kimtaehoondev.jobpostingcollector.service;

import java.util.List;
import org.kimtaehoondev.jobpostingcollector.controller.JobPostingResponseDto;

public interface JobPostingService {
    List<JobPostingResponseDto> findAll();
}
