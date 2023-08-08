package org.kimtaehoondev.jpcollector.factory;

import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ChromeWebDriverFactory implements WebDriverFactory {
    @Override
    public WebDriver make() {
        log.info("Chrome 웹드라이버가 생성되었습니다");
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
}
