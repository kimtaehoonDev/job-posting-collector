package org.kimtaehoondev.jobpostingcollector.jobposting.community.impl;

import java.util.ArrayList;
import java.util.List;
import org.kimtaehoondev.jobpostingcollector.jobposting.community.JobPostingCommunity;
import org.kimtaehoondev.jobpostingcollector.jobposting.community.JobPostingCommunityType;
import org.kimtaehoondev.jobpostingcollector.jobposting.dto.request.JobPostingData;
import org.kimtaehoondev.jobpostingcollector.utils.UrlParser;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.stereotype.Component;

@Component
public class RocketPunchCommunity implements JobPostingCommunity {
    private JobPostingCommunityType communityType = JobPostingCommunityType.ROCKET_PUNCH;
    private Status status = Status.GOOD;

    @Override
    public JobPostingCommunityType getCommunityType() {
        return communityType;
    }

    @Override
    public void changeStatus(Status status) {
        this.status = status;
    }

    @Override
    public boolean isConnected() {
        // TODO
        return true;
    }

    @Override
    public boolean isStatusBad() {
        return Status.BAD == status;
    }

    @Override
    public List<WebElement> getJobPostingElements(WebDriver driver) {
        return driver.findElements(By.cssSelector("#search-results .company"));
    }

    @Override
    public JobPostingData makeJobPostingFrom(WebElement element) {
        JobPostingData.JobPostingDataBuilder builder = JobPostingData.builder();

        String companyName =
            element.findElement(By.cssSelector(".company-name a")).getText().trim();
        builder.companyName(companyName);

        String occupation =
            element.findElement(By.cssSelector(".company-jobs-detail .job-name")).getText().trim();
        builder.occupation(occupation);

        String linkString = element.findElement(By.cssSelector(".company-jobs-detail .job-name a"))
            .getAttribute("href");
        builder.link(UrlParser.parse(linkString));

        List<String> infos = makeInfos(element);
        builder.infos(infos);

        builder.communityType(communityType);
        return builder.build();
    }

    private List<String> makeInfos(WebElement element) {
        List<String> infos = new ArrayList<>();

        element.findElements(By.cssSelector(".job-badges .reward-banner span")).stream()
            .map(each -> each.getText().trim())
            .forEach(infos::add);
        element.findElements(By.cssSelector(".job-badges .label div")).stream()
            .map(each -> each.getText().trim())
            .forEach(infos::add);
        return infos;
    }
}
