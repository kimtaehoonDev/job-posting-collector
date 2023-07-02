package org.kimtaehoondev.jobpostingcollector.jobposting.community;

import java.net.URL;
import java.time.Duration;
import java.util.List;
import org.kimtaehoondev.jobpostingcollector.jobposting.JobPosting;
import org.kimtaehoondev.jobpostingcollector.utils.UrlParser;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
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
        return null;
    }

    private void applyOccupationFilter(WebDriver driver) throws InterruptedException {
        WebElement filterOpenBtn = driver.findElement(
            By.cssSelector("#occupation_level1_filter .filter_item .btn_filter button"));
        filterOpenBtn.click();

        List<WebElement> depth1Btns =
            driver.findElements(By.cssSelector(".filter_depth1_list .filter_depth1_item .filter_depth1_btn"));
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
