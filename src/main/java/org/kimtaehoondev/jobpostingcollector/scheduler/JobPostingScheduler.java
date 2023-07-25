package org.kimtaehoondev.jobpostingcollector.scheduler;

import java.util.List;
import javax.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.kimtaehoondev.jobpostingcollector.jobposting.dto.response.JobPostingCrawlingResult;
import org.kimtaehoondev.jobpostingcollector.email.service.EmailService;
import org.kimtaehoondev.jobpostingcollector.jobposting.JobPostingResolver;
import org.kimtaehoondev.jobpostingcollector.jobposting.dto.response.JobPostingResponseDto;
import org.kimtaehoondev.jobpostingcollector.jobposting.service.JobPostingService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JobPostingScheduler {
    private final JobPostingResolver resolver;
    private final JobPostingService jobPostingService;
    private final EmailService emailService;

    @PostConstruct
    void initData() {
        updateServer();
    }

    @Scheduled(cron = "0 30 6 * * ?")
    void executeRegularUpdate() {
        updateServer();
        List<JobPostingResponseDto> postings = jobPostingService.getNewlyJobPosting();
        emailService.sendJobPostings(postings);
    }

    private void updateServer() {
        List<JobPostingCrawlingResult> crawlingResults = resolver.crawling();
        jobPostingService.updateAll(crawlingResults);
    }
}
