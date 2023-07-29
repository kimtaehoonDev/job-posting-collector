package org.kimtaehoondev.jobpostingcollector.email.auth.storage.certificate;

public interface CertificateTemporaryStorage {
    int TIME_LIMIT = 10;

    String putAuthInfo(String key, String value);

    boolean isValid(String key, String value);
}
