package org.kimtaehoondev.jobpostingcollector.email.auth.storage.certificate;

public interface CertificateTemporaryStorage {
    String putAuthInfo(String key, String value);

    boolean isValid(String key, String value);
}
