package org.kimtaehoondev.jobpostingcollector.jobposting.community;

import java.util.List;
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

    List<WebElement> scrap(WebDriver driver);

    JobPosting makeJobPostingFrom(WebElement element);
}
