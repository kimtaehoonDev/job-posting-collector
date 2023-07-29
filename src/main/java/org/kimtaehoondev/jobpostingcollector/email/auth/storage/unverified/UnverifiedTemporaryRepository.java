package org.kimtaehoondev.jobpostingcollector.email.auth.storage.unverified;

public interface UnverifiedTemporaryRepository {
    int TIME_LIMIT = 3;
    String putAuthInfo(String key, String value);

    boolean isValid(String key, String value);
}
