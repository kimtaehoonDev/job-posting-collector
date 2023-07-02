package org.kimtaehoondev.jobpostingcollector.jobposting;

import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.kimtaehoondev.jobpostingcollector.jobposting.community.JobPostingCommunity;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

@RequiredArgsConstructor
public class JobPostingResolver {
    private final WebDriver driver;
    private final JobPostingStore repository;
    private final List<JobPostingCommunity> communities;

    void updateJobPosting() {
        for (JobPostingCommunity community : communities) {
            List<JobPosting> result = scrap(community);
            repository.updateAll(result);
        }
    }

    private List<JobPosting> scrap(JobPostingCommunity community) {
        if (!community.isConnected()) {
            throw new RuntimeException(community.getUrl() + "연결되지 않습니다");
        }
        String url = community.getUrl();
        driver.get(url);
        // TODO 대기
        List<WebElement> elements = community.scrap(driver);
        return elements.stream()
            .map(community::makeJobPostingFrom)
            .collect(Collectors.toList());
    }
}
