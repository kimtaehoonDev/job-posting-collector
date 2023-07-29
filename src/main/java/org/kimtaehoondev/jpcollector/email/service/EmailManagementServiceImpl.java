package org.kimtaehoondev.jpcollector.email.service;

import lombok.RequiredArgsConstructor;
import org.kimtaehoondev.jpcollector.email.Email;
import org.kimtaehoondev.jpcollector.email.auth.storage.certificate.CertificateTemporaryStorage;
import org.kimtaehoondev.jpcollector.email.auth.storage.unverified.UnverifiedTemporaryRepository;
import org.kimtaehoondev.jpcollector.exception.impl.CodeInvalidException;
import org.kimtaehoondev.jpcollector.exception.impl.EmailDuplicateException;
import org.kimtaehoondev.jpcollector.exception.impl.EmailNotFoundException;
import org.kimtaehoondev.jpcollector.exception.impl.EmailPasswordInvalidException;
import org.kimtaehoondev.jpcollector.exception.impl.EmailUnAuthorizedException;
import org.kimtaehoondev.jpcollector.web.dto.request.EmailDeleteDto;
import org.kimtaehoondev.jpcollector.web.dto.request.EmailRegisterDto;
import org.kimtaehoondev.jpcollector.email.dto.response.EmailWithPwdDto;
import org.kimtaehoondev.jpcollector.email.repository.EmailRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EmailManagementServiceImpl implements EmailManagementService {
    private final EmailRepository emailRepository;
    private final UnverifiedTemporaryRepository unverifiedTemporaryRepository;
    private final CertificateTemporaryStorage certificateTemporaryStorage;
    private final PasswordEncoder passwordEncoder;
    private final EmailSendingService emailSendingService;;

    @Override
    public Long register(EmailRegisterDto dto) {
        validateEmailDuplicated(dto.getEmail());
        if (!certificateTemporaryStorage.isValid(dto.getEmail(), dto.getCode())) {
            throw new EmailUnAuthorizedException();
        }

        String encoded = passwordEncoder.encode(dto.getPwd());
        Email saved = emailRepository.save(Email.create(dto.getEmail(), encoded));
        return saved.getId();
    }

    @Override
    public Long delete(EmailDeleteDto dto) {
        EmailWithPwdDto result = emailRepository.findByEmail(dto.getEmail())
            .orElseThrow(EmailNotFoundException::new);
        if (!passwordEncoder.matches(dto.getPwd(), result.getPwd())) {
            throw new EmailPasswordInvalidException();
        }
        emailRepository.deleteById(result.getId());
        return result.getId();
    }

    @Override
    public void verifyAuthCode(String email, String code) {
        validateEmailDuplicated(email);
        if (!unverifiedTemporaryRepository.isValid(email, code)) {
            throw new CodeInvalidException();
        }
        certificateTemporaryStorage.putAuthInfo(email, code);
    }

    @Override
    public void sendAuthCode(String email, String code) {
        validateEmailDuplicated(email);
        emailSendingService.sendAuthCode(email, code);
        unverifiedTemporaryRepository.putAuthInfo(email, code);
    }

    private void validateEmailDuplicated(String email) {
        boolean isDuplicated = emailRepository.existsByEmail(email);
        if (isDuplicated) {
            throw new EmailDuplicateException();
        }
    }
}
