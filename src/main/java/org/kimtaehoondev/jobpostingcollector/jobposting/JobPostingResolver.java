package org.kimtaehoondev.jobpostingcollector.jobposting;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.kimtaehoondev.jobpostingcollector.jobposting.community.JobPostingCommunity;
import org.kimtaehoondev.jobpostingcollector.jobposting.repository.JobPostingStore;
import org.openqa.selenium.WebDriver;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JobPostingResolver {
    private final WebDriver driver;
    private final JobPostingStore repository;
    private final List<JobPostingCommunity> communities;

    @PostConstruct
    void initData() {
        updateJobPosting();
    }

    public void updateJobPosting() {
        List<JobPosting> result = new ArrayList<>();
        for (JobPostingCommunity community : communities) {
            result.addAll(community.scrap(driver));
        }
        repository.updateAll(result);
    }
}
