package org.kimtaehoondev.jobpostingcollector.email.auth.storage.unverified;

public interface UnverifiedTemporaryRepository {
    String putAuthInfo(String key, String value);

    boolean isValid(String key, String value);
}
