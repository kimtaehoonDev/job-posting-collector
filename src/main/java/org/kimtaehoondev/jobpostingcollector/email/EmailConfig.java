package org.kimtaehoondev.jobpostingcollector.email;

import java.util.Properties;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:application.yml")
public class EmailConfig {

    @Getter
    @Value("${mail-info.username}")
    private String username;

    @Getter
    @Value("${mail-info.password}")
    private String password;

    @Getter
    @Value("${mail-info.encoding}")
    private String encType;

    @Value("${mail-info.properties.host}")
    private String host;

    @Value("${mail-info.properties.port}")
    private int port;

    @Value("${mail-info.properties.auth}")
    private String auth;

    @Bean
    Properties emailProperties() {
        Properties prop = new Properties();
        prop.put("mail.smtp.host", host);
        prop.put("mail.smtp.port", port);
        prop.put("mail.smtp.auth", auth);

        return prop;
    }
}
