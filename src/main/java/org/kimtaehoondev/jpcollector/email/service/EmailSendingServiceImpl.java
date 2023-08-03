package org.kimtaehoondev.jpcollector.email.service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.kimtaehoondev.jpcollector.config.AdminProperties;
import org.kimtaehoondev.jpcollector.email.EmailSender;
import org.kimtaehoondev.jpcollector.email.dto.response.EmailResponseDto;
import org.kimtaehoondev.jpcollector.email.repository.EmailRepository;
import org.kimtaehoondev.jpcollector.jobposting.dto.response.JobPostingResponseDto;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
@RequiredArgsConstructor
public class EmailSendingServiceImpl implements EmailSendingService {
    private final EmailRepository emailRepository;
    private final TemplateEngine templateEngine;
    private final EmailSender emailSender;
    private final AdminProperties adminProperties;

    @Override
    public void sendJobPostings(List<JobPostingResponseDto> jobPostings) {
        String title = LocalDate.now() + "일자 채용 안내 - 김태훈";
        String path = "email/job-posting.html";

        Map<String, Object> variables = new HashMap<>();
        variables.put("postings", jobPostings);

        if (jobPostings.isEmpty()) {
            path = "email/job-posting-empty.html";
            variables.put("url", adminProperties.getUrl());
        }

        variables.put("postings", jobPostings);

        List<EmailResponseDto> total = emailRepository.findAllBy();
        for (EmailResponseDto emailResponseDto : total) {
            sendEmail(emailResponseDto.getEmail(), title, path, variables);
        }
    }

    @Override
    public void sendAuthCode(String email, String authCode) {
        String title = "[채용 정보 크롤러] 인증 코드";

        Map<String, Object> variables = new HashMap<>();
        variables.put("code", authCode);

        Context context = new Context(Locale.KOREAN, variables);
        context.setVariable("title", title);

        sendEmail(email, title,
            "email/auth-code.html", variables);
    }

    private void sendEmail(String email, String title, String templatePath, Map<String, Object> variables) {
        Context context = new Context(Locale.KOREAN, variables);
        context.setVariable("title", title);

        String message = templateEngine.process(templatePath, context);
        emailSender.sendHtml(email, title, message);
    }
}
