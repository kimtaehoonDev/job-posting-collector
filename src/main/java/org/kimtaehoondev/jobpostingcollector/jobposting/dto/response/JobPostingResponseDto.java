package org.kimtaehoondev.jobpostingcollector.jobposting.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.kimtaehoondev.jobpostingcollector.jobposting.JobPosting;

@AllArgsConstructor
@Getter
public class JobPostingResponseDto {
    private final String occupation;

    private final String companyName;

    private final String link;

    private final String infos;

    private boolean isNew;

    public static JobPostingResponseDto from(JobPosting jobPosting) {
        String occupation = jobPosting.getOccupation();
        String companyName = jobPosting.getCompanyName();
        String link = jobPosting.getUrlString();
        boolean isNew = jobPosting.isNew();

        return new JobPostingResponseDto(occupation, companyName, link, jobPosting.getInfos(), isNew);
    }
}
