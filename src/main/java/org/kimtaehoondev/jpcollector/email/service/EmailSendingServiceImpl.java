package org.kimtaehoondev.jpcollector.email.service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.kimtaehoondev.jpcollector.config.AdminProperties;
import org.kimtaehoondev.jpcollector.email.EmailSender;
import org.kimtaehoondev.jpcollector.email.dto.response.EmailResponseDto;
import org.kimtaehoondev.jpcollector.email.repository.EmailRepository;
import org.kimtaehoondev.jpcollector.jobposting.dto.response.JobPostingResponseDto;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailSendingServiceImpl implements EmailSendingService {
    private final EmailRepository emailRepository;
    private final TemplateEngine templateEngine;
    private final EmailSender emailSender;
    private final AdminProperties adminProperties;

    @Override
    public void sendJobPostings(List<JobPostingResponseDto> jobPostings) {
        List<EmailResponseDto> total = emailRepository.findAllBy();

        String title = LocalDate.now() + "일자 채용 안내 - 김태훈";
        String path = "email/job-posting.html";

        Map<String, Object> variables = new HashMap<>();
        variables.put("postings", jobPostings);

        if (jobPostings.isEmpty()) {
            path = "email/job-posting-empty.html";
            variables.put("url", adminProperties.getUrl());
        }

        variables.put("postings", jobPostings);
        String htmlMessage = makeHtml(title, path, variables);
        log.info("새롭게 등록된 채용공고 {} 건을 구독자 전원({}명)에게 보내줍니다", jobPostings.size(), total.size());
        for (EmailResponseDto emailResponseDto : total) {
            emailSender.sendHtml(emailResponseDto.getEmail(), title, htmlMessage);
        }
    }


    @Override
    public void sendAuthCode(String email, String authCode) {
        log.info("{}에게 인증코드를 전송합니다", email);

        String title = "[채용 정보 크롤러] 인증 코드";

        Map<String, Object> variables = new HashMap<>();
        variables.put("code", authCode);

        Context context = new Context(Locale.KOREAN, variables);
        context.setVariable("title", title);
        String htmlMessage = makeHtml(title, "email/auth-code.html", variables);
        emailSender.sendHtml(email, title, htmlMessage);
    }


    private String makeHtml(String title, String path, Map<String, Object> variables) {
        Context context = new Context(Locale.KOREAN, variables);
        context.setVariable("title", title);

        return templateEngine.process(path, context);
    }

}
