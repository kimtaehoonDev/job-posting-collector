package org.kimtaehoondev.jobpostingcollector.service;

import java.util.LinkedList;
import java.util.List;
import java.util.StringJoiner;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.kimtaehoondev.jobpostingcollector.dto.JobPostingResponseDto;
import org.kimtaehoondev.jobpostingcollector.email.EmailSender;
import org.kimtaehoondev.jobpostingcollector.jobposting.JobPosting;
import org.kimtaehoondev.jobpostingcollector.jobposting.repository.JobPostingStore;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JobPostingServiceImpl implements JobPostingService {
    private final JobPostingStore jobPostingStore;
    private final EmailSender emailSender;

    @Override
    public List<JobPostingResponseDto> findAll() {
        return jobPostingStore.findAll().stream()
            .map(JobPostingResponseDto::from)
            .collect(Collectors.toList());
    }

    @Override
    public void sendResult() {
        List<JobPosting> total = jobPostingStore.findAll();
        String message = makePrettier(total);
        emailSender.sendToAll(message);
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
