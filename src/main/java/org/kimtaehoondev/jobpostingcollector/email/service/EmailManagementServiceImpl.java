package org.kimtaehoondev.jobpostingcollector.email.service;

import lombok.RequiredArgsConstructor;
import org.kimtaehoondev.jobpostingcollector.email.Email;
import org.kimtaehoondev.jobpostingcollector.email.auth.storage.certificate.CertificateTemporaryStorage;
import org.kimtaehoondev.jobpostingcollector.email.auth.storage.unverified.UnverifiedTemporaryRepository;
import org.kimtaehoondev.jobpostingcollector.web.dto.request.EmailDeleteDto;
import org.kimtaehoondev.jobpostingcollector.web.dto.request.EmailRegisterDto;
import org.kimtaehoondev.jobpostingcollector.email.dto.response.EmailWithPwdDto;
import org.kimtaehoondev.jobpostingcollector.email.repository.EmailRepository;
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
        boolean isValid = certificateTemporaryStorage.isValid(dto.getEmail(), dto.getCode());
        if (!isValid) {
            throw new RuntimeException("이미 만료되었거나 적절하지 않은 이메일입니다");
        }

        String encoded = passwordEncoder.encode(dto.getPwd());
        Email saved = emailRepository.save(Email.create(dto.getEmail(), encoded));
        return saved.getId();
    }

    @Override
    public Long delete(EmailDeleteDto dto) {
        EmailWithPwdDto result = emailRepository.findByEmail(dto.getEmail())
            .orElseThrow(() -> new RuntimeException("존재하지않는 아이디"));
        boolean matches = passwordEncoder.matches(dto.getPwd(), result.getPwd());
        if (!matches) {
            throw new RuntimeException("존재하지않는 아이디");
        }
        emailRepository.deleteById(result.getId());
        return result.getId();
    }

    @Override
    public void verifyAuthCode(String email, String code) {
        validateEmailDuplicated(email);
        if (!unverifiedTemporaryRepository.isValid(email, code)) {
            throw new RuntimeException("보내준 코드가 만료되었거나, 일치하지 않습니다");
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
            throw new RuntimeException("중복된 아이디");
        }
    }
}
