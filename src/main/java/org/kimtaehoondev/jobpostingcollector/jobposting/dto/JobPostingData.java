package org.kimtaehoondev.jobpostingcollector.jobposting.dto;

import java.net.URL;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Builder;
import lombok.Getter;
import org.kimtaehoondev.jobpostingcollector.jobposting.JobPosting;

public class JobPostingData {
    @Getter
    private String occupation;
    @Getter
    private String companyName;
    private URL link;

    @Getter
    private String infos;

    @Builder
    private JobPostingData(String occupation, String companyName, URL link, List<String> infos) {
        this.occupation = occupation;
        this.companyName = companyName;
        this.link = link;
        this.infos = infos.stream().map(String::toLowerCase).collect(Collectors.joining(" | "));
    }

    public boolean isIncludeInfo(String info) {
        return infos.contains(info.toLowerCase());
    }

    public JobPosting from() {
        return JobPosting.create(occupation, companyName, link, infos);
    }

}
