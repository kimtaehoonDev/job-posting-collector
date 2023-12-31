package org.kimtaehoondev.jpcollector.jobposting.dto.request;

import java.net.URL;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Builder;
import lombok.Getter;
import org.kimtaehoondev.jpcollector.jobposting.JobPosting;
import org.kimtaehoondev.jpcollector.jobposting.community.JobPostingCommunityType;

public class JobPostingData {
    @Getter
    private String occupation;
    @Getter
    private String companyName;
    private URL link;

    @Getter
    private String infos;

    private JobPostingCommunityType communityType;

    @Builder
    private JobPostingData(String occupation, String companyName, URL link, List<String> infos,
                           JobPostingCommunityType communityType) {
        this.occupation = occupation;
        this.companyName = companyName;
        this.link = link;
        this.infos = infos.stream().map(String::toLowerCase).collect(Collectors.joining(" | "));
        this.communityType = communityType;
    }

    public boolean isIncludeInfo(String info) {
        return infos.contains(info.toLowerCase());
    }

    public JobPosting from() {
        return JobPosting.create(occupation, companyName, link, infos, communityType);
    }

}
