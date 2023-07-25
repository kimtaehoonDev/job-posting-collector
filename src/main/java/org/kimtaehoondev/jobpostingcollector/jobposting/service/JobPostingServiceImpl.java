package org.kimtaehoondev.jobpostingcollector.jobposting.service;

import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.kimtaehoondev.jobpostingcollector.dto.JobPostingResponseDto;
import org.kimtaehoondev.jobpostingcollector.jobposting.JobPosting;
import org.kimtaehoondev.jobpostingcollector.jobposting.dto.JobPostingData;
import org.kimtaehoondev.jobpostingcollector.jobposting.repository.JobPostingRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JobPostingServiceImpl implements JobPostingService {
    private final JobPostingRepository jobPostingRepository;

    @Override
    public List<JobPostingResponseDto> findAll() {
        return jobPostingRepository.findAll().stream()
            .map(JobPostingResponseDto::from)
            .collect(Collectors.toList());
    }

    @Override
    public void updateAll(List<JobPostingData> jobPostingDataList) {
        List<JobPosting> jobPostings = jobPostingDataList.stream()
            .map(JobPostingData::from)
            .collect(Collectors.toList());

        for (JobPosting jobPosting : jobPostings) {
            boolean isExist = jobPostingRepository.existsByOccupationAndCompanyName(
                jobPosting.getOccupation(), jobPosting.getCompanyName());
            if (isExist) {
                jobPosting.renew();
            }
        }
        jobPostingRepository.deleteAll();
        jobPostingRepository.saveAll(jobPostings);
    }
}
