package org.kimtaehoondev.jobpostingcollector.email.auth.storage;

public interface TemporaryStorage {
    String put(String email, String random);
}
