package org.kimtaehoondev.jobpostingcollector.email.service;

import java.time.LocalDate;
import java.util.List;
import java.util.StringJoiner;
import lombok.RequiredArgsConstructor;
import org.kimtaehoondev.jobpostingcollector.email.auth.generator.AuthCodeGenerator;
import org.kimtaehoondev.jobpostingcollector.email.Email;
import org.kimtaehoondev.jobpostingcollector.email.EmailSender;
import org.kimtaehoondev.jobpostingcollector.email.auth.storage.certificate.CertificateTemporaryStorage;
import org.kimtaehoondev.jobpostingcollector.email.auth.storage.unverified.UnverifiedTemporaryRepository;
import org.kimtaehoondev.jobpostingcollector.web.dto.request.EmailDeleteDto;
import org.kimtaehoondev.jobpostingcollector.web.dto.request.EmailRegisterDto;
import org.kimtaehoondev.jobpostingcollector.email.dto.response.EmailResponseDto;
import org.kimtaehoondev.jobpostingcollector.email.dto.response.EmailWithPwdDto;
import org.kimtaehoondev.jobpostingcollector.email.repository.EmailRepository;
import org.kimtaehoondev.jobpostingcollector.jobposting.dto.response.JobPostingResponseDto;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {
    private final EmailRepository emailRepository;
    private final EmailSender emailSender;
    private final PasswordEncoder passwordEncoder;
    private final AuthCodeGenerator authCodeGenerator;
    private final UnverifiedTemporaryRepository unverifiedTemporaryRepository;
    private final CertificateTemporaryStorage certificateTemporaryStorage;

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

    private void validateEmailDuplicated(String email) {
        boolean isDuplicated = emailRepository.existsByEmail(email);
        if (isDuplicated) {
            throw new RuntimeException("중복된 아이디");
        }
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
    public void sendJobPostings(List<JobPostingResponseDto> jobPostings) {
        String title = LocalDate.now() + "일자 채용 안내 - 김태훈";
        String message = makePrettierHtml(jobPostings);
        List<EmailResponseDto> total = emailRepository.findAllBy();
        for (EmailResponseDto emailResponseDto : total) {
            emailSender.sendHtml(emailResponseDto.getEmail(), title, message);
        }
    }

    @Override
    public void sendAuthCode(String email) {
        validateEmailDuplicated(email);
        String random = authCodeGenerator.generate();
        emailSender.sendHtml(email,"[채용 정보 크롤러] 인증 코드", "인증코드는 [" +random+"] 입니다");
        unverifiedTemporaryRepository.putAuthInfo(email, random);
    }

    @Override
    public void verifyAuthCode(String email, String code) {
        validateEmailDuplicated(email);
        if (!unverifiedTemporaryRepository.isValid(email, code)) {
            throw new RuntimeException("보내준 코드가 만료되었거나, 일치하지 않습니다");
        }
        certificateTemporaryStorage.putAuthInfo(email, code);
    }


    private String makePrettierHtml(List<JobPostingResponseDto> postings) {

        StringJoiner sj = new StringJoiner("\n");
        sj.add("<h1>" + LocalDate.now() + "</h1>");
        sj.add("<h2>새롭게 등록된 직무 목록</h2>");
        for (JobPostingResponseDto posting : postings) {
            sj.add("<div>" + makePrettierHtml(posting)+ "</div>");
        }
        return sj.toString();
    }

    private String makePrettierHtml(JobPostingResponseDto posting) {
        StringJoiner sj = new StringJoiner("\n");
        sj.add("<div>");
        sj.add("<p>직종: " + posting.getOccupation()+"</p>");
        sj.add("<p>회사명: " + posting.getCompanyName()+"</p>");
        sj.add("<p>" + posting.getLink().toString()+"</p>");
        sj.add("<p>" + posting.getInfos() + "</p>");
        sj.add("</div>");
        sj.add("<hr>");
        return sj.toString();
    }
}
