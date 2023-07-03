package org.kimtaehoondev.jobpostingcollector;

import lombok.RequiredArgsConstructor;
import org.kimtaehoondev.jobpostingcollector.jobposting.JobPostingResolver;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JobPostingScheduler {
    private final JobPostingResolver resolver;

    @Scheduled(cron = "0 30 6 * * ?")
    void updateJobPostingRegularly() {
        resolver.updateJobPosting();
    }

    @Scheduled(cron = "0 30 * * * ?")
    void updateMissingJobPosting() {
        resolver.requestToBadStatusCommunity();
    }
}
