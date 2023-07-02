package org.kimtaehoondev.jobpostingcollector.jobposting;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.kimtaehoondev.jobpostingcollector.jobposting.community.JobPostingCommunity;
import org.openqa.selenium.WebDriver;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JobPostingResolver {
    private final WebDriver driver;
    private final JobPostingStore repository;
    private final List<JobPostingCommunity> communities;

    void updateJobPosting() {
        for (JobPostingCommunity community : communities) {
            List<JobPosting> result = community.scrap(driver);
            repository.updateAll(result);
        }
    }
}
