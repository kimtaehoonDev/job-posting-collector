package org.kimtaehoondev.jpcollector.jobposting.community;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.kimtaehoondev.jpcollector.exception.impl.ElementNotFoundException;
import org.kimtaehoondev.jpcollector.exception.impl.ThreadNotWorkingException;
import org.kimtaehoondev.jpcollector.jobposting.dto.request.JobPostingData;
import org.kimtaehoondev.jpcollector.utils.UrlParser;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public interface JobPostingCommunity {
    int WAITING_MILLISECOND = 2000;
    Logger log = LoggerFactory.getLogger(JobPostingCommunity.class);

    JobPostingCommunityType getCommunityType();

    void changeStatus(Status status);

    default String getUrl() {
        return getCommunityType().getUrl().toString();
    }


    /**
     * 채용 공고를 확인하기 위한 페이지를 접속합니다.
     * url만으로 채용 공고 확인이 가능한 경우 구현하지 않습니다.
     */
    default void accessJobPostingPage(WebDriver driver) {
        log.info("{} 커뮤니티는 태그들을 조작하지 않고 넘어갑니다", getCommunityType().name());
    }

    /**
     * 각각의 채용공고 태그에 접근할 수 있는 CssSelector를 반환한다
     */
    String getJobPostingElementCssSelector();

    /**
     * 사이트에 접속해 JobPostingData를 List로 가져옵니다. 순서는 다음과 같습니다.
     * 1. 인터넷 창을 엽니다.
     * 2. 채용공고에 접근하기 위해 화면을 조작합니다.
     * (1번 만으로 데이터에 접근이 가능하다면, 해당 단계는 생략이 가능합니다)
     * 3. 무한스크롤을 처리하기 위해 스크롤을 하단으로 내립니다.
     * 4. 각각의 채용공고에 접근할 수 있는 List<WebElement>를 가져옵니다.
     * 5. WebElement를 JobPostingData로 변환한 뒤 결과를 반환합니다
     */
    default List<JobPostingData> scrap(WebDriver driver) {
        log.info("{} 커뮤니티 스크랩을 시작합니다", getCommunityType().name());
        try {
            openInternetWindow(driver);
            accessJobPostingPage(driver);
            doScrollDown((JavascriptExecutor) driver, 5);

            List<WebElement> elements = getJobPostingElements(driver);
            List<JobPostingData> postings = elements.stream()
                .map(this::makeJobPostingFrom)
                .filter(this::filter)
                .collect(Collectors.toList());
            log.info("{} 커뮤니티 스크랩에 성공했습니다", getCommunityType().name());
            return postings;
        } catch (NoSuchElementException e) {
            log.error("원소를 찾을 수 없습니다 : {}", e.getMessage());
            throw new ElementNotFoundException();
        }
    }


    default List<WebElement> getJobPostingElements(WebDriver driver) {
        String selector = getJobPostingElementCssSelector();
        return driver.findElements(By.cssSelector(selector));
    }

    default JobPostingData makeJobPostingFrom(WebElement element) {
        JobPostingData.JobPostingDataBuilder builder = JobPostingData.builder();

        String companyName =
            element.findElement(By.cssSelector(getCompanyCssSelector())).getText().trim();
        builder.companyName(companyName);

        String occupation =
            element.findElement(By.cssSelector(getOccupationCssSelector())).getText().trim();
        builder.occupation(occupation);

        String linkString =
            element.findElement(By.cssSelector(getLinkCssSelector())).getAttribute("href");
        builder.link(UrlParser.parse(linkString));

        List<String> infos = makeInfos(element);
        builder.infos(infos);

        builder.communityType(getCommunityType());
        return builder.build();
    }

    String getLinkCssSelector();

    String getOccupationCssSelector();

    String getCompanyCssSelector();

    List<String> getInfoListCssSelector();

    default List<String> makeInfos(WebElement element) {
        List<String> infos = new ArrayList<>();

        List<String> cssSelectors = getInfoListCssSelector();
        for (String cssSelector : cssSelectors) {
            element.findElements(By.cssSelector(cssSelector)).stream()
                .map(each -> each.getText().trim())
                .forEach(infos::add);
        }
        return infos;
    }

    default void openInternetWindow(WebDriver driver) {
        log.info("인터넷 창을 엽니다");
        try {
            String url = getUrl();
            driver.get(url);
            Thread.sleep(WAITING_MILLISECOND);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new ThreadNotWorkingException();
        }
    }


    private static void doScrollDown(JavascriptExecutor driver, int repeatCnt) {
        log.info("스크롤을 {}회 내립니다", repeatCnt);
        for (int i = 0; i < repeatCnt; i++) {
            try {
                Thread.sleep(WAITING_MILLISECOND);
                driver.executeScript(
                    "window.scrollTo(0, document.body.scrollHeight);");
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new ThreadNotWorkingException();
            }
        }
    }

    /**
     * 연산된 결과값에 필터링을 걸어야 하는 경우 사용합니다.
     * false를 반환하면 해당되는 JobPosting은 결과에 포함되지 않습니다
     */

    default boolean filter(JobPostingData jobPostingData) {
        return true;
    }


    /**
     * driver에서 By를 사용해 WebElement를 가져옵니다.
     * 해당되는 element가 없다면, ElementNotFoundException을 반환합니다.
     */
    default WebElement findElement(WebDriver driver, By by) {
        WebElement element = driver.findElement(by);
        if (element == null) {
            throw new ElementNotFoundException();
        }
        return element;
    }

    boolean isStatusBad();


    enum Status {
        GOOD, BAD;
    }
}
