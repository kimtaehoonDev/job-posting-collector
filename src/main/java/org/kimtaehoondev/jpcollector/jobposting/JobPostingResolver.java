package org.kimtaehoondev.jpcollector.jobposting;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.kimtaehoondev.jpcollector.factory.WebDriverFactory;
import org.kimtaehoondev.jpcollector.jobposting.community.JobPostingCommunity;
import org.kimtaehoondev.jpcollector.jobposting.dto.request.JobPostingData;
import org.kimtaehoondev.jpcollector.jobposting.dto.response.JobPostingCrawlingResult;
import org.openqa.selenium.WebDriver;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class JobPostingResolver {
    private final WebDriverFactory webDriverFactory;
    private final List<JobPostingCommunity> communities;

    public List<JobPostingCrawlingResult> crawling() {
        log.info("크롤링을 시작합니다");
        List<JobPostingCrawlingResult> total = new ArrayList<>();
        WebDriver webDriver = webDriverFactory.make();
        for (JobPostingCommunity community : communities) {
            List<JobPostingData> postings = scrap(webDriver, community);
            if (community.isStatusBad()) {
                continue;
            }
            total.add(new JobPostingCrawlingResult(community, postings));
        }

        log.info("창을 닫습니다");
        webDriver.close();
        return total;
    }

    private List<JobPostingData> scrap(WebDriver webDriver, JobPostingCommunity community) {
        try {
            List<JobPostingData> result = community.scrap(webDriver);
            community.changeStatus(JobPostingCommunity.Status.GOOD);
            return result;
        } catch (Exception e) {
            log.error("{} 커뮤니티의 크롤링에 실패했습니다", community.getCommunityType().name());
            community.changeStatus(JobPostingCommunity.Status.BAD);
            return Collections.emptyList();
        }
    }
}
