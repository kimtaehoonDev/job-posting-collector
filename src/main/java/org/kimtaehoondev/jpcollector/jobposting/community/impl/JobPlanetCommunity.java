package org.kimtaehoondev.jpcollector.jobposting.community.impl;

import java.time.Duration;
import java.util.List;
import org.kimtaehoondev.jpcollector.jobposting.community.JobPostingCommunity;
import org.kimtaehoondev.jpcollector.jobposting.community.JobPostingCommunityType;
import org.kimtaehoondev.jpcollector.jobposting.dto.request.JobPostingData;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.stereotype.Component;

@Component
public class JobPlanetCommunity implements JobPostingCommunity {
    private JobPostingCommunityType communityType = JobPostingCommunityType.JOB_PLANET;
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
    public void accessJobPostingPage(WebDriver driver) {
        try {
            applyOccupationFilter(driver);
            applyYearsOfExperienceFilter(driver);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getJobPostingElementCssSelector() {
        return "#JobPostingApp .recruitment-content .item-list .item-card";
    }

    @Override
    public String getLinkCssSelector() {
        return "a";
    }

    @Override
    public String getOccupationCssSelector() {
        return ".item-card__information .item-card__title";
    }

    @Override
    public String getCompanyCssSelector() {
        return ".item-card__information .item-card__company .item-card__name";
    }

    @Override
    public List<String> getInfoListCssSelector() {
        return List.of(".item-card__information .item-card__skill",
            ".item-card__information .item-card__reward");
    }

    @Override
    public boolean filter(JobPostingData jobPostingData) {
        if (jobPostingData.isIncludeInfo("spring") || jobPostingData.isIncludeInfo("java")) {
            return true;
        }
        return false;
    }

    private void applyYearsOfExperienceFilter(WebDriver driver) throws InterruptedException {
        WebElement filterOpenBtn = driver.findElement(
            By.cssSelector("#years_of_experience_filter .filter_item .btn_filter button"));
        filterOpenBtn.click();

        List<WebElement> handles = driver.findElements(
            By.cssSelector("#sliderFilter_years_of_experience .ui-slider-handle"));
        WebElement startHandle = handles.get(0);
        WebElement endHandle = handles.get(1);

        Actions action = new Actions(driver);
        action.moveToElement(endHandle)
            .clickAndHold()
            .moveToElement(startHandle)
            .release()
            .build().perform();

        WebElement applyBtn =
            driver.findElements(By.cssSelector("#years_of_experience_filter .panel_bottom button"))
                .stream().filter(btn -> btn.getText().trim().equals("적용"))
                .findAny()
                .orElseThrow(() -> new RuntimeException("HTML 구조를 잘못 매핑했습니다"));
        applyBtn.click();

        Thread.sleep(1000);// TODO 가능하면 명시적으로 변경

    }

    private void applyOccupationFilter(WebDriver driver) throws InterruptedException {
        WebElement filterOpenBtn = driver.findElement(
            By.cssSelector("#occupation_level1_filter .filter_item .btn_filter button"));
        filterOpenBtn.click();

        List<WebElement> depth1Btns =
            driver.findElements(
                By.cssSelector(".filter_depth1_list .filter_depth1_item .filter_depth1_btn"));
        for (WebElement depth1Btn : depth1Btns) {
            if (depth1Btn.getText().trim().equals("개발")) {
                depth1Btn.click();
            }
        }

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofMillis(10000));
        WebElement depth2Container = wait.until(
            ExpectedConditions.presenceOfElementLocated(
                By.cssSelector("#occupation_level1_filter .filter_depth2 .jply_checkbox_list")));

        List<WebElement> occupationDepth2Elements = depth2Container.findElements(By.tagName("li"));
        for (WebElement occupationDepth2Element : occupationDepth2Elements) {
            String occupationValue =
                occupationDepth2Element.findElement(By.className("jf_b1")).getText().trim();
            if (occupationValue.equals("백엔드 개발")) {
                WebElement depth2Btn = occupationDepth2Element.findElement(By.tagName("i"));
                depth2Btn.click();
                break;
            }
        }

        // 적용 버튼 누르기
        WebElement applyBtn =
            driver.findElements(By.cssSelector("#occupation_level1_filter .panel_bottom button"))
                .stream()
                .filter(btn -> btn.getText().trim().equals("적용"))
                .findAny()
                .orElseThrow(() -> new RuntimeException("HTML 구조를 잘못 매핑했습니다"));
        applyBtn.click();
        Thread.sleep(1000);// TODO 가능하면 명시적으로 변경
    }

}
