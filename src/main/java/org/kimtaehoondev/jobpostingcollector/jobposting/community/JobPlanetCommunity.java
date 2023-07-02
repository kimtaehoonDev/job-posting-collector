package org.kimtaehoondev.jobpostingcollector.jobposting.community;

import java.net.URL;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import org.kimtaehoondev.jobpostingcollector.jobposting.JobPosting;
import org.kimtaehoondev.jobpostingcollector.utils.UrlParser;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.stereotype.Component;

@Component
public class JobPlanetCommunity implements JobPostingCommunity {
    private final URL url = UrlParser.parse("https://www.jobplanet.co.kr/job");

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
            applyOccupationFilter(driver);
            applyYearsOfExperienceFilter(driver);
            applyReviewScoreFilter(driver);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<WebElement> getJobPostingElements(WebDriver driver) {
        return driver.findElements(By.cssSelector(".recruitment-content .item-list .item-card"));
    }

    @Override
    public JobPosting makeJobPostingFrom(WebElement element) {
        JobPosting.JobPostingBuilder builder = JobPosting.builder();

        String linkString = element.findElement(By.tagName("a")).getAttribute("href");
        builder.link(UrlParser.parse(linkString));

        List<WebElement> jobPostingInfoElements =
            element.findElements(By.cssSelector(".item-card__information div"));

        List<String> infos = new ArrayList<>();
        for (WebElement each : jobPostingInfoElements) {
            String classValue = each.getAttribute("class");
            if (classValue.contains("item-card__title")) {
                builder.occupation(each.getText());
                continue;
            }
            if (classValue.contains("item-card__company")) {
                WebElement companyNameElement =
                    element.findElement(By.className("item-card__name"));
                builder.companyName(companyNameElement.getText());
                continue;
            }
            if (!each.getText().isBlank()) {
                infos.add(each.getText());
            }
        }
        builder.infos(infos);
        return builder.build();
    }

    private void applyReviewScoreFilter(WebDriver driver) throws InterruptedException {
        WebElement filterOpenBtn = driver.findElement(
            By.cssSelector("#review_score_filter .filter_item .btn_filter button"));
        filterOpenBtn.click();

        List<WebElement> handles = driver.findElements(
            By.cssSelector("#sliderFilter_review_score .ui-slider-handle"));
        WebElement startHandle = handles.get(0);
        WebElement endHandle = handles.get(1);

        int startX = startHandle.getLocation().getX();
        int endX = endHandle.getLocation().getX();
        int targetX = 3 * (endX - startX) / 5;
        Actions action = new Actions(driver);
        action.moveToElement(startHandle)
            .clickAndHold()
            .moveByOffset(targetX, 0)
            .release()
            .build().perform();

        // 적용 버튼 누르기
        WebElement applyBtn =
            driver.findElements(By.cssSelector("#review_score_filter .panel_bottom button"))
                .stream().filter(btn -> btn.getText().trim().equals("적용"))
                .findAny()
                .orElseThrow(() -> new RuntimeException("HTML 구조를 잘못 매핑했습니다"));
        applyBtn.click();

        Thread.sleep(1000);// TODO 가능하면 명시적으로 변경

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
