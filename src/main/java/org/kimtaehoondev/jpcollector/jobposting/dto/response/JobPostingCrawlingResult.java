package org.kimtaehoondev.jpcollector.jobposting.dto.response;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.kimtaehoondev.jpcollector.jobposting.community.JobPostingCommunity;
import org.kimtaehoondev.jpcollector.jobposting.dto.request.JobPostingData;

@AllArgsConstructor
public class JobPostingCrawlingResult {
    @Getter
    private final JobPostingCommunity community;

    @Getter
    private final List<JobPostingData> result;
}
