package org.kimtaehoondev.jobpostingcollector.jobposting.service;

import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.kimtaehoondev.jobpostingcollector.jobposting.dto.response.JobPostingResponseDto;
import org.kimtaehoondev.jobpostingcollector.jobposting.dto.response.JobPostingCrawlingResult;
import org.kimtaehoondev.jobpostingcollector.jobposting.JobPosting;
import org.kimtaehoondev.jobpostingcollector.jobposting.community.JobPostingCommunity;
import org.kimtaehoondev.jobpostingcollector.jobposting.dto.request.JobPostingData;
import org.kimtaehoondev.jobpostingcollector.jobposting.repository.JobPostingRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class JobPostingServiceImpl implements JobPostingService {
    private final JobPostingRepository jobPostingRepository;

    @Override
    public List<JobPostingResponseDto> findAll() {
        return jobPostingRepository.findAll().stream()
            .map(JobPostingResponseDto::from)
            .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void updateAll(List<JobPostingCrawlingResult> crawlingResults) {
        for (JobPostingCrawlingResult result : crawlingResults) {
            List<JobPosting> jobPostings = result.getResult().stream()
                .map(JobPostingData::from)
                .collect(Collectors.toList());
            updateSpecificCommunity(result.getCommunity(), jobPostings);
        }
    }

    @Override
    public List<JobPostingResponseDto> getNewlyJobPosting() {
        return jobPostingRepository.findAllByStatus(JobPosting.Status.NEW);
    }

    private void updateSpecificCommunity(JobPostingCommunity community,
                                         List<JobPosting> jobPostings) {
        for (JobPosting jobPosting : jobPostings) {
            boolean isExist = jobPostingRepository.existsByOccupationAndCompanyName(
                jobPosting.getOccupation(), jobPosting.getCompanyName());
            if (isExist) {
                jobPosting.renew();
            }
        }
        jobPostingRepository.deleteAllByCommunity(community.getCommunityType());
        jobPostingRepository.saveAll(jobPostings);
    }
}
