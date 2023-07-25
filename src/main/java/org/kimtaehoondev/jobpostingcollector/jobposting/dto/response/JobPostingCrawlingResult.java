package org.kimtaehoondev.jobpostingcollector.jobposting.dto.response;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.kimtaehoondev.jobpostingcollector.jobposting.community.JobPostingCommunity;
import org.kimtaehoondev.jobpostingcollector.jobposting.dto.request.JobPostingData;

@AllArgsConstructor
public class JobPostingCrawlingResult {
    @Getter
    private final JobPostingCommunity community;

    @Getter
    private final List<JobPostingData> result;
}
