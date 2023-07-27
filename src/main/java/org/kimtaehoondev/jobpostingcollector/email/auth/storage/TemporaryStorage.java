package org.kimtaehoondev.jobpostingcollector.email.auth.storage;

public interface TemporaryStorage {
    String put(String key, String value);

    boolean isValid(String key, String value);
}
