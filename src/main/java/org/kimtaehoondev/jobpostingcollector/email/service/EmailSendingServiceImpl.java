package org.kimtaehoondev.jobpostingcollector.email.service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.kimtaehoondev.jobpostingcollector.email.EmailSender;
import org.kimtaehoondev.jobpostingcollector.email.dto.response.EmailResponseDto;
import org.kimtaehoondev.jobpostingcollector.email.repository.EmailRepository;
import org.kimtaehoondev.jobpostingcollector.jobposting.dto.response.JobPostingResponseDto;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
@RequiredArgsConstructor
public class EmailSendingServiceImpl implements EmailSendingService {
    private final EmailRepository emailRepository;
    private final TemplateEngine templateEngine;
    private final EmailSender emailSender;

    @Override
    public void sendJobPostings(List<JobPostingResponseDto> jobPostings) {
        String title = LocalDate.now() + "일자 채용 안내 - 김태훈";
        Map<String, Object> variables = new HashMap<>();
        variables.put("postings", jobPostings);
        Context context = new Context(Locale.KOREAN, variables);
        context.setVariable("title", title);
        String message =
            templateEngine.process("/email/job-posting.html", context);
        List<EmailResponseDto> total = emailRepository.findAllBy();
        for (EmailResponseDto emailResponseDto : total) {
            emailSender.sendHtml(emailResponseDto.getEmail(), title, message);
        }
    }

    @Override
    public void sendAuthCode(String email, String authCode) {
        validateEmailDuplicated(email);
        String title = "[채용 정보 크롤러] 인증 코드";
        Map<String, Object> variables = new HashMap<>();
        variables.put("code", authCode);
        Context context = new Context(Locale.KOREAN, variables);
        context.setVariable("title", title);
        String message =
            templateEngine.process("/email/auth-code.html", context);

        emailSender.sendHtml(email,title, message);
    }



    private void validateEmailDuplicated(String email) {
        boolean isDuplicated = emailRepository.existsByEmail(email);
        if (isDuplicated) {
            throw new RuntimeException("중복된 아이디");
        }
    }
}
