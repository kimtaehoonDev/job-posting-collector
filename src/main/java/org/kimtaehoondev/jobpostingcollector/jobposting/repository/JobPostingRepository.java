package org.kimtaehoondev.jobpostingcollector.jobposting.repository;

import java.util.List;
import org.kimtaehoondev.jobpostingcollector.jobposting.JobPosting;
import org.kimtaehoondev.jobpostingcollector.jobposting.community.JobPostingCommunityType;
import org.kimtaehoondev.jobpostingcollector.jobposting.dto.response.JobPostingResponseDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JobPostingRepository extends JpaRepository<JobPosting, Long> {
    boolean existsByOccupationAndCompanyName(String occupation, String companyName);

    void deleteAllByCommunity(JobPostingCommunityType communityType);

    List<JobPostingResponseDto> findAllByStatus(JobPosting.Status status);
}
