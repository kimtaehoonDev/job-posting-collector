package org.kimtaehoondev.jpcollector.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;

@Configuration
@EnableRedisRepositories
@RequiredArgsConstructor
public class RedisConfig {
    private final RedisProperties redisProperties;
    private final RedisCustomProperties redisCustomProperties;

    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
        RedisStandaloneConfiguration conf =
            new RedisStandaloneConfiguration(redisProperties.getHost(), redisProperties.getPort());
        conf.setPassword(redisProperties.getPassword());

        return new LettuceConnectionFactory(conf);
    }

    @Bean
    public RedisConnectionFactory redisConnectionFactorySub() {
        RedisStandaloneConfiguration conf =
            new RedisStandaloneConfiguration(redisCustomProperties.getHost(),
                redisCustomProperties.getPort());
        conf.setPassword(redisCustomProperties.getPassword());

        return new LettuceConnectionFactory(conf);
    }

    @Bean // 디폴트
    public RedisTemplate<?, ?> redisTemplate(@Qualifier("redisConnectionFactory")
                                                  RedisConnectionFactory connectionFactory) {
        RedisTemplate<?, ?> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);
        return template;
    }

    @Bean
    public RedisTemplate<?, ?> redisTemplateSub(@Qualifier("redisConnectionFactorySub")
                                                   RedisConnectionFactory connectionFactory) {
        RedisTemplate<?, ?> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);
        return template;
    }
}
