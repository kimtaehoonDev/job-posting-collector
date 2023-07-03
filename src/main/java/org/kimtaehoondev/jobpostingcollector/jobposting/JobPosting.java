package org.kimtaehoondev.jobpostingcollector.jobposting;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;
import lombok.ToString;

@ToString
public class JobPosting {
    private final String occupation;
    private final String companyName;
    private final URL link;
    private final List<String> infos;
    private final LocalDateTime createdAt;

    @Builder
    private JobPosting(String occupation, String companyName, URL link, List<String> infos) {
        this.occupation = occupation;
        this.companyName = companyName;
        this.link = link;
        this.infos = infos;
        this.createdAt = LocalDateTime.now();
    }
}
