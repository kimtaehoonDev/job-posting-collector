package org.kimtaehoondev.jobpostingcollector.email.auth.storage.unverified;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class RedisUnverifiedTemporaryRepository implements UnverifiedTemporaryRepository {
    private final RedisTemplate<String, String> template;

    @Override
    public String putAuthInfo(String key, String value) {
        ValueOperations<String, String> operations = template.opsForValue();
        String old = operations.get(key);
        operations.set(key,value, Duration.ofMinutes(3));
        return old;
    }

    @Override
    public boolean isValid(String key, String value) {
        ValueOperations<String, String> operations = template.opsForValue();
        String stored = operations.get(key);
        if (stored == null) {
            return false;
        }
        return Objects.equals(stored, value);
    }
}
