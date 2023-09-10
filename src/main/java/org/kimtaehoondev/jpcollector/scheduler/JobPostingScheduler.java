package org.kimtaehoondev.jpcollector.scheduler;

import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.PostConstruct;

import org.kimtaehoondev.jpcollector.email.service.EmailSendingService;
import org.kimtaehoondev.jpcollector.jobposting.JobPostingResolver;
import org.kimtaehoondev.jpcollector.jobposting.dto.response.JobPostingCrawlingResult;
import org.kimtaehoondev.jpcollector.jobposting.dto.response.JobPostingResponseDto;
import org.kimtaehoondev.jpcollector.jobposting.service.JobPostingService;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class JobPostingScheduler {
    private final JobPostingResolver resolver;
    private final JobPostingService jobPostingService;
    private final EmailSendingService emailSendingService;

    /**
     * 실행하면 메세지를 보낸다. 수동으로 크롤링을 수행하기 위해 필요함
     */
    @PostConstruct
    void init() {
        executeRegularUpdate();
    }

    @Async
    @Scheduled(cron = "0 30 6 * * ?")
    void executeRegularUpdate() {
        log.info("{}일자 스케쥴러를 실행합니다.", LocalDate.now());
        updateServer();
        List<JobPostingResponseDto> postings = jobPostingService.getNewlyJobPosting();
        emailSendingService.sendJobPostings(postings);
    }

    private void updateServer() {
        List<JobPostingCrawlingResult> crawlingResults = resolver.crawling();
        jobPostingService.updateAll(crawlingResults);
    }

}
