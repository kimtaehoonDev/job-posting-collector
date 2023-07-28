package org.kimtaehoondev.jobpostingcollector.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "redis-custom")
@Getter
@Setter
public class RedisCustomProperties {
    private String host;
    private Integer port;
}
