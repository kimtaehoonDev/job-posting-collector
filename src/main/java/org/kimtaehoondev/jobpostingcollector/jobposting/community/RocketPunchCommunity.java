package org.kimtaehoondev.jobpostingcollector.jobposting.community;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import org.kimtaehoondev.jobpostingcollector.jobposting.JobPosting;
import org.kimtaehoondev.jobpostingcollector.utils.UrlParser;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.stereotype.Component;

@Component
public class RocketPunchCommunity implements JobPostingCommunity {
    // 필터링 조건: SW개발, JAVA, 신입, 인턴, 웹서비스
    private final URL url = UrlParser.parse(
        "https://www.rocketpunch.com/jobs?job=1&specialty=Java&career_type=1&career_type=4&tag=%EC%9B%B9%EC%84%9C%EB%B9%84%EC%8A%A4");


    @Override
    public String getUrl() {
        return url.toString();
    }

    @Override
    public boolean isConnected() {
        // TODO
        return true;
    }

    @Override
    public List<WebElement> getJobPostingElements(WebDriver driver) {
        return driver.findElements(By.cssSelector("#search-results .company"));
    }

    @Override
    public JobPosting makeJobPostingFrom(WebElement element) {
        JobPosting.JobPostingBuilder builder = JobPosting.builder();

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
