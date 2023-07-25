package org.kimtaehoondev.jobpostingcollector.jobposting.community;

import java.util.List;
import java.util.stream.Collectors;
import org.kimtaehoondev.jobpostingcollector.exception.impl.ConnectException;
import org.kimtaehoondev.jobpostingcollector.exception.impl.HttpParsingException;
import org.kimtaehoondev.jobpostingcollector.jobposting.dto.JobPostingData;
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

    default void process(WebDriver driver) {

    }

    List<WebElement> getJobPostingElements(WebDriver driver);

    JobPostingData makeJobPostingFrom(WebElement element);

    default List<JobPostingData> scrap(WebDriver driver) {
        if (!isConnected()) {
            throw new ConnectException(getUrl());
        }

        try {
            String url = getUrl();
            driver.get(url);
            wait(url, 1000);
            process(driver);
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
