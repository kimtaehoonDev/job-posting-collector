package org.kimtaehoondev.jobpostingcollector;

import java.util.List;
import javax.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.kimtaehoondev.jobpostingcollector.email.service.EmailService;
import org.kimtaehoondev.jobpostingcollector.jobposting.JobPostingResolver;
import org.kimtaehoondev.jobpostingcollector.jobposting.dto.JobPostingData;
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
        updateJobPostingRegularly();
    }

    @Scheduled(cron = "0 30 6 * * ?")
    void updateJobPostingRegularly() {
        List<JobPostingData> jobPostings = resolver.getJobPostings();
        jobPostingService.updateAll(jobPostings);
        // TODO 이메일을 보낸다
    }
}
