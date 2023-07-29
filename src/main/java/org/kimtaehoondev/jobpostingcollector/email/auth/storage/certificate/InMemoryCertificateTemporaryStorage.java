package org.kimtaehoondev.jobpostingcollector.email.auth.storage.certificate;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

//@Repository
public class InMemoryCertificateTemporaryStorage implements CertificateTemporaryStorage {
    public static final int MINUTE = 60;

    private final Map<String, Data> store = new HashMap<>();

    @Override
    public String putAuthInfo(String key, String value) {
        Data old = getValidData(key);

        store.put(key, new Data(value));
        if (old == null) {
            return null;
        }
        return old.value;
    }

    @Override
    public boolean isValid(String key, String value) {
        Data data = getValidData(key);
        if (data == null) {
            return false;
        }
        return Objects.equals(value, data.value);
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
            long secondGap = ChronoUnit.SECONDS.between(createdAt, LocalDateTime.now());
            if (secondGap >= TIME_LIMIT * MINUTE) {
                return true;
            }
            return false;
        }
    }
}
