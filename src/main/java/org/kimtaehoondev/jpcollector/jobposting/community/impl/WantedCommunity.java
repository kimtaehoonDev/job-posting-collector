package org.kimtaehoondev.jpcollector.jobposting.community.impl;

import java.time.Duration;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.kimtaehoondev.jpcollector.exception.impl.ThreadNotWorkingException;
import org.kimtaehoondev.jpcollector.jobposting.community.JobPostingCommunity;
import org.kimtaehoondev.jpcollector.jobposting.community.JobPostingCommunityType;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class WantedCommunity implements JobPostingCommunity {
    private JobPostingCommunityType communityType = JobPostingCommunityType.WANTED;
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
    public boolean isStatusBad() {
        return Status.BAD == status;
    }

    @Override
    public void accessJobPostingPage(WebDriver driver) {
        try {
            applyStackFilter(driver);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new ThreadNotWorkingException();
        }
    }

    @Override
    public String getJobPostingElementCssSelector() {
        return ".List_List_container__JnQMS .Card_className__u5rsb";
    }

    @Override
    public String getLinkCssSelector() {
        return "a";
    }

    @Override
    public String getOccupationCssSelector() {
        return ".body .job-card-position";
    }

    @Override
    public String getCompanyCssSelector() {
        return ".body .job-card-company-name";
    }

    @Override
    public List<String> getInfoListCssSelector() {
        return List.of(".body div:not(.job-card-position):not(.job-card-company-name)");
    }

    private void applyStackFilter(WebDriver driver) throws InterruptedException {
        log.info("경력 필터를 클릭합니다");
        WebElement filterOpenBtn = driver.findElement(
            By.cssSelector(".FilterInWdlist_skillsFilterContainer__UZGLH .FilterButton_ButtonClassName__PWHFf"));
        filterOpenBtn.click();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofMillis(10000));
        WebElement stackContainer = wait.until(ExpectedConditions.presenceOfElementLocated(
            By.cssSelector(".RecommendedSkills_RecommendedSkills__EeSZ9")));

        log.info("Spring Framework 버튼을 클릭합니다");
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

        log.info("적용 버튼을 클릭합니다");
        WebElement applyBtn =
            driver.findElements(By.cssSelector(".Footer_Footer__rgOdb button")).stream()
                .filter(each -> each.getText().trim().equals("적용하기"))
                .findAny()
                .orElseThrow(() -> new RuntimeException("잘못된 HTML 구조"));
        applyBtn.click();
        Thread.sleep(1000);
    }

}
