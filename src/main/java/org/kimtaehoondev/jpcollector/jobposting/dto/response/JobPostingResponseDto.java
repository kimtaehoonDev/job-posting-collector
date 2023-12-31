package org.kimtaehoondev.jpcollector.jobposting.dto.response;

import java.net.URL;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.kimtaehoondev.jpcollector.jobposting.JobPosting;

@AllArgsConstructor
@Getter
public class JobPostingResponseDto {
    private final String occupation;

    private final String companyName;

    private final URL link;

    private final String infos;

    private JobPosting.Status status;

    public static JobPostingResponseDto from(JobPosting jobPosting) {
        String occupation = jobPosting.getOccupation();
        String companyName = jobPosting.getCompanyName();
        URL link = jobPosting.getLink();
        JobPosting.Status status = jobPosting.getStatus();

        return new JobPostingResponseDto(occupation, companyName, link, jobPosting.getInfos(), status);
    }
}
