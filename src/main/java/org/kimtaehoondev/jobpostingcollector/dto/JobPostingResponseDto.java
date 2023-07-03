package org.kimtaehoondev.jobpostingcollector.dto;

import java.util.List;
import java.util.StringJoiner;
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
        StringJoiner sj = new StringJoiner(" | ");
        List<String> infos = jobPosting.getInfos();
        for (String info : infos) {
            sj.add(info);
        }
        boolean isNew = jobPosting.isNew();

        return new JobPostingResponseDto(occupation, companyName, link, sj.toString(), isNew);
    }
}
