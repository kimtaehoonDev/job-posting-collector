package org.kimtaehoondev.jobpostingcollector.email.auth.storage.unverified;

import java.time.Duration;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;

@Repository
public class RedisUnverifiedTemporaryRepository implements UnverifiedTemporaryRepository {
    private final RedisTemplate<String, String> template;

    public RedisUnverifiedTemporaryRepository(@Qualifier("redisTemplate")
                                              RedisTemplate<String , String> template) {
        this.template = template;
    }

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
