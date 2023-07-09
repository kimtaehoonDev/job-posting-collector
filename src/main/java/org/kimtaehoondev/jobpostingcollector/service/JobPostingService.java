package org.kimtaehoondev.jobpostingcollector.service;

import java.util.List;
import org.kimtaehoondev.jobpostingcollector.dto.JobPostingResponseDto;

public interface JobPostingService {
    List<JobPostingResponseDto> findAll();

    void sendJobPostingUpdateToAll();
}
