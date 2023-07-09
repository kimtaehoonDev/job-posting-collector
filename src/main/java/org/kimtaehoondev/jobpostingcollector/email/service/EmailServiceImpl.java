package org.kimtaehoondev.jobpostingcollector.email.service;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import java.util.StringJoiner;
import lombok.RequiredArgsConstructor;
import org.kimtaehoondev.jobpostingcollector.email.EmailSender;
import org.kimtaehoondev.jobpostingcollector.jobposting.JobPosting;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {
    private final EmailSender emailSender;

    @Override
    public void sendJobPostingUpdateToAll(List<JobPosting> jobPostings) {
        String title = LocalDate.now() + "일자 채용 안내 - 김태훈";
        String message = makePrettierHtml(jobPostings);
        emailSender.sendHtmlToAll(title, message);
    }

    private String makePrettierHtml(List<JobPosting> postings) {
        List<JobPosting> newPostings = new LinkedList<>();
        List<JobPosting> oldPostings = new LinkedList<>();

        for (JobPosting posting : postings) {
            if (posting.isNew()) {
                newPostings.add(posting);
            } else {
                oldPostings.add(posting);
            }
        }

        StringJoiner sj = new StringJoiner("\n");
        sj.add("<h1>" + LocalDate.now() + "</h1>");
        sj.add("<h2>NEW</h2>");
        for (JobPosting posting : newPostings) {
            sj.add("<div>" + makePrettierHtml(posting)+ "</div>");
        }
        sj.add("<h2>PREV</h2>");
        for (JobPosting posting : oldPostings) {
            sj.add("<div>" + makePrettierHtml(posting)+ "</div>");
        }
        return sj.toString();
    }

    private String makePrettierHtml(JobPosting posting) {
        StringJoiner sj = new StringJoiner("\n");
        sj.add("<div>");
        sj.add("<p>직종: " + posting.getOccupation()+"</p>");
        sj.add("<p>회사명: " + posting.getCompanyName()+"</p>");
        sj.add("<p>" + posting.getUrlString()+"</p>");
        sj.add("<p>" + String.join(" | ", posting.getInfos() + "</p>"));
        sj.add("</div>");
        sj.add("<hr>");
        return sj.toString();
    }
}
