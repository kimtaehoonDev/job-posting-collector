package org.kimtaehoondev.jobpostingcollector.email.auth.storage;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class InMemoryTemporaryStorage implements TemporaryStorage {
    private final Map<String, Data> store = new HashMap<>();
    @Override
    public String put(String key, String value) {
        Data old = getValidData(key);

        store.put(key, new Data(value));
        if (old == null) {
            return null;
        }
        return old.value;
    }

    private Data getValidData(String key) {
        if (!store.containsKey(key)) {
            return null;
        }

        Data value = store.get(key);
        if (value.isTimeout()) {
            store.remove(key);
            return null;
        }
        return value;
    }

    class Data {
        private final String value;
        private final LocalDateTime createdAt;

        public Data(String value) {
            this.value = value;
            this.createdAt = LocalDateTime.now();
        }

        public boolean isTimeout() {
            long minuteGap = ChronoUnit.MINUTES.between(LocalDateTime.now(), createdAt);
            if (minuteGap > 3) {
                return true;
            }
            return false;
        }
    }
}
