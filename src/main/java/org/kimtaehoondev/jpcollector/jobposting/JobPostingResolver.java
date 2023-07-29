package org.kimtaehoondev.jpcollector.jobposting;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.kimtaehoondev.jpcollector.jobposting.dto.response.JobPostingCrawlingResult;
import org.kimtaehoondev.jpcollector.exception.impl.ConnectException;
import org.kimtaehoondev.jpcollector.exception.impl.HttpParsingException;
import org.kimtaehoondev.jpcollector.jobposting.community.JobPostingCommunity;
import org.kimtaehoondev.jpcollector.jobposting.dto.request.JobPostingData;
import org.openqa.selenium.WebDriver;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JobPostingResolver {
    private final WebDriver driver;
    private final List<JobPostingCommunity> communities;

    public List<JobPostingCrawlingResult> crawling() {
        List<JobPostingCrawlingResult> total = new ArrayList<>();
        for (JobPostingCommunity community : communities) {
            List<JobPostingData> postings = scrap(community);
            if (community.isStatusBad()) {
                continue;
            }
            total.add(new JobPostingCrawlingResult(community, postings));
        }
        return total;
    }

    private List<JobPostingData> scrap(JobPostingCommunity community) {
        try {
            List<JobPostingData> result = community.scrap(driver);
            community.changeStatus(JobPostingCommunity.Status.GOOD);
            return result;
        } catch (HttpParsingException | ConnectException e) {
            community.changeStatus(JobPostingCommunity.Status.BAD);
            return Collections.emptyList();
        }
    }
}
