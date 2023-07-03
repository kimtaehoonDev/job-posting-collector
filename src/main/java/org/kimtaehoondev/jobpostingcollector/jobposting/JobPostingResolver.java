package org.kimtaehoondev.jobpostingcollector.jobposting;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.kimtaehoondev.jobpostingcollector.exception.impl.ConnectException;
import org.kimtaehoondev.jobpostingcollector.exception.impl.HttpParsingException;
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
        List<JobPosting> total = new ArrayList<>();
        for (JobPostingCommunity community : communities) {
            List<JobPosting> postings = scrap(community);
            total.addAll(postings);
        }
        repository.updateAll(total);
    }

    private List<JobPosting> scrap(JobPostingCommunity community) {
        try {
            community.changeStatus(JobPostingCommunity.Status.GOOD);
            return community.scrap(driver);
        } catch (HttpParsingException | ConnectException e) {
            community.changeStatus(JobPostingCommunity.Status.BAD);
            return Collections.emptyList();
        }
    }

    public void requestToBadStatusCommunity() {
        List<JobPosting> total = new ArrayList<>();
        List<JobPostingCommunity> badStatusCommunities = communities.stream()
            .filter(JobPostingCommunity::isStatusBad)
            .collect(Collectors.toList());

        for (JobPostingCommunity community : badStatusCommunities) {
            List<JobPosting> postings = scrap(community);
            total.addAll(postings);
        }
        repository.updatePart(total);
    }
}
