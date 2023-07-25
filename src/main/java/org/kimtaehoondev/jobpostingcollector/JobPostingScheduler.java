package org.kimtaehoondev.jobpostingcollector;

import lombok.RequiredArgsConstructor;
import org.kimtaehoondev.jobpostingcollector.jobposting.JobPostingResolver;
import org.kimtaehoondev.jobpostingcollector.jobposting.service.JobPostingService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JobPostingScheduler {
    private final JobPostingResolver resolver;
    private final JobPostingService jobPostingService;

    @Scheduled(cron = "0 30 6 * * ?")
    void updateJobPostingRegularly() {
        List<JobPosting> jobPostings = resolver.getJobPostings(); // TODO DTO를 만들어서 반환
        jobPostingService.updateAll(jobPostings);
        // TODO 이메일을 보낸다
    }

    @Scheduled(cron = "0 */20 * * * ?")
    void updateMissingJobPosting() {
        resolver.updateFromBadStatusCommunity();
    }
}
