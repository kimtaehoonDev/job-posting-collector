package org.kimtaehoondev.jobpostingcollector.jobposting.community.impl;

import java.net.URL;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.kimtaehoondev.jobpostingcollector.jobposting.JobPosting;
import org.kimtaehoondev.jobpostingcollector.jobposting.community.JobPostingCommunity;
import org.kimtaehoondev.jobpostingcollector.utils.UrlParser;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class WantedCommunity implements JobPostingCommunity {
    private final URL url = UrlParser.parse(
        "https://www.wanted.co.kr/wdlist/518/872?country=kr&job_sort=company.response_rate_order&years=0");

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
    public void process(WebDriver driver) {
        try {
            applyStackFilter(driver);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private void applyStackFilter(WebDriver driver) throws InterruptedException {
        WebElement filterOpenBtn = driver.findElement(
            By.cssSelector(".FilterInWdlist_skillsFilterContainer__UZGLH button"));
        filterOpenBtn.click();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofMillis(10000));
        WebElement stackContainer = wait.until(ExpectedConditions.presenceOfElementLocated(
            By.cssSelector(".RecommendedSkills_RecommendedSkills__EeSZ9")));

        List<WebElement> skillTagBtns =
            stackContainer.findElements(By.cssSelector("li button"));
        for (WebElement skillTagBtn : skillTagBtns) {
            boolean isSpringFrameworkBtn =
                skillTagBtn.getText().trim().equals("Spring Framework");
            if (isSpringFrameworkBtn) {
                skillTagBtn.click();
                break;
            }
        }

        // 적용버튼을 누른다
        WebElement applyBtn =
            driver.findElements(By.cssSelector(".Footer_Footer__rgOdb button")).stream()
                .filter(each -> each.getText().trim().equals("적용하기"))
                .findAny()
                .orElseThrow(() -> new RuntimeException("잘못된 HTML 구조"));
        applyBtn.click();
        Thread.sleep(1000); // TODO
    }

    @Override
    public List<WebElement> getJobPostingElements(WebDriver driver) {
        return driver.findElements(By.cssSelector(".List_List_container__JnQMS .Card_className__u5rsb"));
    }

    @Override
    public JobPosting makeJobPostingFrom(WebElement element) {
        JobPosting.JobPostingBuilder builder = JobPosting.builder();

        String linkString = element.findElement(By.tagName("a")).getAttribute("href");
        builder.link(UrlParser.parse(linkString));

        List<WebElement> infosInElement = element.findElements(By.cssSelector(".body div"));

        List<String> infos = new ArrayList<>();
        for (WebElement each : infosInElement) {
            String classValue = each.getAttribute("class");
            if (classValue.contains("job-card-position")) {
                builder.occupation(each.getText());
                continue;
            }
            if (classValue.contains("job-card-company-name")) {
                builder.companyName(each.getText());
                continue;
            }
            if (!each.getText().isBlank()) {
                infos.add(each.getText());
            }
        }
        builder.infos(infos);

        return builder.build();
    }
}
