package org.kimtaehoondev.jpcollector.jobposting.community.impl;

import java.time.Duration;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.kimtaehoondev.jpcollector.exception.impl.ElementNotFoundException;
import org.kimtaehoondev.jpcollector.jobposting.community.JobPostingCommunity;
import org.kimtaehoondev.jpcollector.jobposting.community.JobPostingCommunityType;
import org.kimtaehoondev.jpcollector.jobposting.dto.request.JobPostingData;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.stereotype.Component;

@Slf4j
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
            log.error("스레드 sleep에 문제가 발생했습니다");
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
        try {
            log.info("잡플래닛 경험 필터를 적용합니다");

            // 경험 필터를 여는 버튼을 클릭합니다
            WebElement filterOpenBtn = findElement(driver,
                By.cssSelector("#years_of_experience_filter .filter_item .btn_filter button"));
            filterOpenBtn.click();

            // 경험 필터는 슬라이더로 구성되어 있습니다.
            // 슬라이더의 양측 버튼을 찾습니다.
            List<WebElement> handles = driver.findElements(
                By.cssSelector("#sliderFilter_years_of_experience .ui-slider-handle"));
            if (handles.size() < 2) {
                throw new ElementNotFoundException();
            }
            WebElement startHandle = handles.get(0);
            WebElement endHandle = handles.get(1);

            // 슬라이더의 끝을 시작점까지 당깁니다.
            Actions action = new Actions(driver);
            action.moveToElement(endHandle)
                .clickAndHold()
                .moveToElement(startHandle)
                .release()
                .build().perform();

            // 적용 버튼을 찾아 클릭합니다
            WebElement applyBtn =
                driver.findElements(By.cssSelector("#years_of_experience_filter .panel_bottom button"))
                    .stream().filter(btn -> btn.getText().trim().equals("적용"))
                    .findAny()
                    .orElseThrow(ElementNotFoundException::new);
            applyBtn.click();

            // 적용되기를 기다립니다.
            Thread.sleep(1000);// TODO 가능하면 명시적으로 변경
        } catch (ElementNotFoundException e) {
            log.error("applyYearsOfExperienceFilter 메서드에서 + {}", e.getMessage());
            throw e;
        }
    }

    private void applyOccupationFilter(WebDriver driver) throws InterruptedException {
        try {
            log.info("잡플래닛 직업 필터를 적용합니다");

            // 직업 필터를 여는 버튼을 클릭합니다
            WebElement filterOpenBtn = findElement(driver,
                By.cssSelector("#occupation_level1_filter .filter_item .btn_filter button"));
            filterOpenBtn.click();

            clickOccupationFirstDepthBtn(driver);
            clickOccupationSecondDepthBtn(driver);

            // 적용 버튼 누르기
            WebElement applyBtn =
                driver.findElements(By.cssSelector("#occupation_level1_filter .panel_bottom button"))
                    .stream()
                    .filter(btn -> btn.getText().trim().equals("적용"))
                    .findAny()
                    .orElseThrow(ElementNotFoundException::new);
            applyBtn.click();
            Thread.sleep(1000);// TODO 가능하면 명시적으로 변경

        } catch (ElementNotFoundException e) {
            log.error("applyOccupationFilter 메서드에서 + {}", e.getMessage());
            throw e;
        }
    }

    private static void clickOccupationSecondDepthBtn(WebDriver driver) {
        WebElement depth2Container;

        try {
            // depth2Container가 나올 떄까지 기다린다
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofMillis(10000));

            // depth2Container를 꺼낸다
            depth2Container = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.cssSelector("#occupation_level1_filter .filter_depth2 .jply_checkbox_list")));
        } catch (TimeoutException e) {
            throw new ElementNotFoundException();
        }

        List<WebElement> occupationDepth2Elements = depth2Container.findElements(By.tagName("li"));
        for (WebElement occupationDepth2Element : occupationDepth2Elements) {
            String occupationValue =
                occupationDepth2Element.findElement(By.className("jf_b1")).getText().trim();
            if (occupationValue.equals("백엔드 개발")) {
                WebElement depth2Btn = occupationDepth2Element.findElement(By.tagName("i"));
                depth2Btn.click();
                return;
            }
        }
        throw new ElementNotFoundException();
    }

    private static void clickOccupationFirstDepthBtn(WebDriver driver) {
        List<WebElement> depth1Btns =
            driver.findElements(
                By.cssSelector(".filter_depth1_list .filter_depth1_item .filter_depth1_btn"));
        for (WebElement depth1Btn : depth1Btns) {
            if (depth1Btn.getText().trim().equals("개발")) {
                depth1Btn.click();
                return;
            }
        }
        throw new ElementNotFoundException();
    }

}
