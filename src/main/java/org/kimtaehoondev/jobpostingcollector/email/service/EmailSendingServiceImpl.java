package org.kimtaehoondev.jobpostingcollector.email.service;

import java.time.LocalDate;
import java.util.List;
import java.util.StringJoiner;
import lombok.RequiredArgsConstructor;
import org.kimtaehoondev.jobpostingcollector.email.EmailSender;
import org.kimtaehoondev.jobpostingcollector.email.dto.response.EmailResponseDto;
import org.kimtaehoondev.jobpostingcollector.email.repository.EmailRepository;
import org.kimtaehoondev.jobpostingcollector.jobposting.dto.response.JobPostingResponseDto;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailSendingServiceImpl implements EmailSendingService {
    private final EmailRepository emailRepository;
    private final EmailSender emailSender;

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
    public void sendAuthCode(String email, String authCode) {
        validateEmailDuplicated(email);
        emailSender.sendHtml(email,"[채용 정보 크롤러] 인증 코드", "인증코드는 [" +authCode+"] 입니다");
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

    private void validateEmailDuplicated(String email) {
        boolean isDuplicated = emailRepository.existsByEmail(email);
        if (isDuplicated) {
            throw new RuntimeException("중복된 아이디");
        }
    }
}
