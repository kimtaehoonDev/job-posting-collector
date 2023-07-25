package org.kimtaehoondev.jobpostingcollector.email.service;

import java.time.LocalDate;
import java.util.List;
import java.util.StringJoiner;
import lombok.RequiredArgsConstructor;
import org.kimtaehoondev.jobpostingcollector.email.Email;
import org.kimtaehoondev.jobpostingcollector.email.EmailSender;
import org.kimtaehoondev.jobpostingcollector.email.dto.request.EmailRegisterRequestDto;
import org.kimtaehoondev.jobpostingcollector.email.dto.response.EmailResponseDto;
import org.kimtaehoondev.jobpostingcollector.email.repository.EmailRepository;
import org.kimtaehoondev.jobpostingcollector.jobposting.dto.response.JobPostingResponseDto;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {
    private final EmailRepository emailRepository;
    private final EmailSender emailSender;

    @Override
    public Long registerEmail(EmailRegisterRequestDto dto) {
        Email saved = emailRepository.save(dto.toEntity());
        return saved.getId();
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
