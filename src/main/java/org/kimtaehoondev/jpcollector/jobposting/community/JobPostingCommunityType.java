package org.kimtaehoondev.jpcollector.jobposting.community;

import java.net.URL;
import lombok.Getter;
import org.kimtaehoondev.jpcollector.utils.UrlParser;

@Getter
public enum JobPostingCommunityType {
    JOB_PLANET("https://www.jobplanet.co.kr/job"),
    ROCKET_PUNCH("https://www.rocketpunch.com/jobs?job=1&tag=%EC%9B%B9%EC%84%9C%EB%B9%84%EC%8A%A4&career_type=1&career_type=4&keywords=java&keywords=spring"),
    WANTED("https://www.wanted.co.kr/wdlist/518/872?country=kr&job_sort=company.response_rate_order&years=0"),
    JUMPIT("https://www.jumpit.co.kr/positions?jobCategory=1&career=0&techStack=Spring%20Boot&techStack=Java");

    private URL url;

    JobPostingCommunityType(String url) {
        this.url = UrlParser.parse(url);
    }
}
