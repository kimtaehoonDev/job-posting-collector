package org.kimtaehoondev.jobpostingcollector.jobposting.community;

import java.util.List;
import java.util.stream.Collectors;
import org.kimtaehoondev.jobpostingcollector.exception.impl.ConnectException;
import org.kimtaehoondev.jobpostingcollector.exception.impl.HttpParsingException;
import org.kimtaehoondev.jobpostingcollector.jobposting.dto.request.JobPostingData;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public interface JobPostingCommunity {
    JobPostingCommunityType getCommunityType();

    void changeStatus(Status status);

    default String getUrl() {
        return getCommunityType().getUrl().toString();
    }

    // TODO
    /**
     * 미리 요청을 보내본 뒤, 서버가 연결가능한지 확인한다
     */
    boolean isConnected();

    /**
     * 채용 공고를 확인하기 위한 페이지를 접속한다.
     * url만으로 채용 공고 확인이 가능한 경우 구현하지 않는다
     */
    default void accessJobPostingPage(WebDriver driver) {

    }

    /**
     * 각각의 채용공고 태그에 접근할 수 있는 CssSelector를 반환한다
     */
    String getJobPostingElementCssSelector();

    default List<WebElement> getJobPostingElements(WebDriver driver) {
        String selector = getJobPostingElementCssSelector();
        return driver.findElements(By.cssSelector(selector));
    }


    JobPostingData makeJobPostingFrom(WebElement element);

    default List<JobPostingData> scrap(WebDriver driver) {
        if (!isConnected()) {
            throw new ConnectException(getUrl());
        }

        try {
            String url = getUrl();
            driver.get(url);
            wait(url, 1000);
            accessJobPostingPage(driver);

            List<WebElement> elements = getJobPostingElements(driver);
            return elements.stream()
                .map(this::makeJobPostingFrom)
                .filter(this::filter)
                .collect(Collectors.toList());
        } catch (NoSuchElementException e) {
            throw new HttpParsingException();
        }
    }

    default boolean filter(JobPostingData jobPostingData) {
        return true;
    }

    private void wait(String urlString, int millis) {
        try {
            // TODO 대기 방식 가능하면 명시적으로 변경
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            throw new RuntimeException(urlString + "시간초과입니다");
        }
    }

    boolean isStatusBad();

    enum Status {
        GOOD, BAD;
    }
}
