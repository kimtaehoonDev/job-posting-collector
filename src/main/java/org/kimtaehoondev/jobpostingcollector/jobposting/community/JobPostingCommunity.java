package org.kimtaehoondev.jobpostingcollector.jobposting.community;

import java.util.List;
import java.util.stream.Collectors;
import org.kimtaehoondev.jobpostingcollector.jobposting.JobPosting;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public interface JobPostingCommunity {
    String getUrl();

    // TODO
    /**
     * 미리 요청을 보내본 뒤, 서버가 연결가능한지 확인한다
     */
    boolean isConnected();

    List<WebElement> getJobPostingElements(WebDriver driver);

    JobPosting makeJobPostingFrom(WebElement element);

    default List<JobPosting> scrap(WebDriver driver) {
        if (!isConnected()) {
            throw new RuntimeException(getUrl() + ": 연결되지 않습니다");
        }
        String url = getUrl();
        driver.get(url);
        wait(url, 1000);
        List<WebElement> elements = getJobPostingElements(driver);
        return elements.stream()
            .map(this::makeJobPostingFrom)
            .collect(Collectors.toList());
    }

    private void wait(String urlString, int millis) {
        try {
            // TODO 대기 방식 가능하면 명시적으로 변경
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            throw new RuntimeException(urlString + "시간초과입니다");
        }
    }

}
