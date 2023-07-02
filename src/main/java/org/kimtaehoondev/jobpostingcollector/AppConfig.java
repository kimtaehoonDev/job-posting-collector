package org.kimtaehoondev.jobpostingcollector;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
    @Bean
    WebDriver webDriver() {
        ChromeOptions options = new ChromeOptions();
        options.setCapability("webdriver.chrome.driver", "/usr/local/bin/chromedriver");
        options.setHeadless(true);
        options.addArguments("--lang=ko");
        options.addArguments("--disable-gpu");
        options.addArguments("--remote-allow-origins=*");
        options.addArguments("user-agent=Mozilla/5.0");

        return new ChromeDriver(options);
    }
}
