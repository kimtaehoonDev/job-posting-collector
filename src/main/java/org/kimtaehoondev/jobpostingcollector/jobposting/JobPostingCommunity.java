package org.kimtaehoondev.jobpostingcollector.jobposting;

import java.util.List;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public interface JobPostingCommunity {
    String getUrl();

    boolean isConnected();

    List<WebElement> scrap(WebDriver driver);

    JobPosting makeJobPostingFrom(WebElement element);
}
