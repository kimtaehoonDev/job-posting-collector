package org.kimtaehoondev.jpcollector.jobposting.repository;

import java.util.List;
import org.kimtaehoondev.jpcollector.jobposting.JobPosting;
import org.kimtaehoondev.jpcollector.jobposting.community.JobPostingCommunityType;
import org.kimtaehoondev.jpcollector.jobposting.dto.response.JobPostingResponseDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JobPostingRepository extends JpaRepository<JobPosting, Long> {
    boolean existsByOccupationAndCompanyName(String occupation, String companyName);

    void deleteAllByCommunity(JobPostingCommunityType communityType);

    List<JobPostingResponseDto> findAllByStatus(JobPosting.Status status);
}
