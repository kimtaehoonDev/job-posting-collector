package org.kimtaehoondev.jobpostingcollector.service;

import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.kimtaehoondev.jobpostingcollector.dto.JobPostingResponseDto;
import org.kimtaehoondev.jobpostingcollector.jobposting.repository.JobPostingStore;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JobPostingServiceImpl implements JobPostingService{
    private final JobPostingStore jobPostingStore;

    @Override
    public List<JobPostingResponseDto> findAll() {
        return jobPostingStore.findAll().stream()
            .map(JobPostingResponseDto::from)
            .collect(Collectors.toList());
    }
}
