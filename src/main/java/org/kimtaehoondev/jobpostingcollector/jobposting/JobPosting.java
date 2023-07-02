package org.kimtaehoondev.jobpostingcollector.jobposting;

import java.net.URL;
import java.util.List;
import lombok.Builder;
import lombok.ToString;

@Builder
@ToString
public class JobPosting {
    private final String occupation;
    private final String companyName;
    private final URL link;
    private final List<String> infos;
}
