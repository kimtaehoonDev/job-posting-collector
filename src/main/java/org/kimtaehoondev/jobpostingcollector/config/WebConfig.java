package org.kimtaehoondev.jobpostingcollector.config;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.HiddenHttpMethodFilter;

@Configuration
public class WebConfig {
    @Bean
    WebDriver webDriver() {
        ChromeOptions options = new ChromeOptions();
        options.setCapability("webdriver.chrome.driver", "/usr/local/bin/chromedriver");
        options.setHeadless(true);
        options.addArguments("--no-sandbox");
        options.addArguments("--lang=ko");
        options.addArguments("--disable-gpu");
        options.addArguments("--remote-allow-origins=*");
        options.addArguments("user-agent=Mozilla/5.0");
        options.addArguments("--incognito"); // 시크릿모드
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--disable-extensions");

        return new ChromeDriver(options);
    }

    @Bean
    public HiddenHttpMethodFilter hiddenHttpMethodFilter() {
        return new HiddenHttpMethodFilter();
    }

}
