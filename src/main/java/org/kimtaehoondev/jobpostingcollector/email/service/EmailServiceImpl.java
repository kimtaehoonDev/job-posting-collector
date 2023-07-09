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
        String message = makePrettier(jobPostings);
        emailSender.sendToAll(title, message);
    }

    private String makePrettier(List<JobPosting> postings) {
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
        sj.add("NEW");
        for (JobPosting posting : newPostings) {
            sj.add(makePrettier(posting));
        }
        sj.add("PREV");
        for (JobPosting posting : oldPostings) {
            sj.add(makePrettier(posting));
        }
        return sj.toString();
    }

    private String makePrettier(JobPosting posting) {
        StringJoiner sj = new StringJoiner("\n");
        sj.add("직종: " + posting.getOccupation());
        sj.add("회사명: " + posting.getCompanyName());
        sj.add("링크: " + posting.getUrlString());
        sj.add("정보: " + String.join(" | ", posting.getInfos()));
        sj.add("#");
        return sj.toString();
    }
}
