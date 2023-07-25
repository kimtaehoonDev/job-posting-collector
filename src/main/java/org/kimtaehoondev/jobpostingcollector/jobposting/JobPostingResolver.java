package org.kimtaehoondev.jobpostingcollector.jobposting;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.kimtaehoondev.jobpostingcollector.exception.impl.ConnectException;
import org.kimtaehoondev.jobpostingcollector.exception.impl.HttpParsingException;
import org.kimtaehoondev.jobpostingcollector.jobposting.community.JobPostingCommunity;
import org.kimtaehoondev.jobpostingcollector.jobposting.dto.JobPostingData;
import org.openqa.selenium.WebDriver;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JobPostingResolver {
    private final WebDriver driver;
    private final List<JobPostingCommunity> communities;

    public List<JobPostingData> getJobPostings() {
        List<JobPostingData> total = new ArrayList<>();
        for (JobPostingCommunity community : communities) {
            List<JobPostingData> postings = scrap(community);
            total.addAll(postings);
        }
        return total;
    }

    private List<JobPostingData> scrap(JobPostingCommunity community) {
        try {
            community.changeStatus(JobPostingCommunity.Status.GOOD);
            return community.scrap(driver);
        } catch (HttpParsingException | ConnectException e) {
            community.changeStatus(JobPostingCommunity.Status.BAD);
            return Collections.emptyList();
        }
    }
}
