package org.kimtaehoondev.jobpostingcollector.jobposting;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@ToString
public class JobPosting {
    @Getter
    private final Identifier identifier;

    @Getter
    private final String occupation;

    @Getter
    private final String companyName;

    private final URL link;

    @Getter
    private final List<String> infos;

    private Status status;

    private final LocalDateTime createdAt;


    @Builder
    private JobPosting(String occupation, String companyName, URL link, List<String> infos) {
        this.identifier = new Identifier(occupation, companyName);
        this.occupation = occupation;
        this.companyName = companyName;
        this.link = link;
        this.infos = infos;
        this.status = Status.NEW;
        this.createdAt = LocalDateTime.now();
    }

    public void makeRenewData() {
        status = Status.RENEW;
    }

    public void makeEndData() {
        status = Status.END;
    }

    public boolean isEnd() {
        return status == Status.END;
    }

    public boolean isNew() {
        return status == Status.NEW;
    }

    public String getUrlString() {
        return link.toString();
    }

    public boolean isIncludeInfo(String info) {
        return infos.stream()
            .map(String::toLowerCase)
            .anyMatch(each -> each.contains(info.toLowerCase()));
    }

    @RequiredArgsConstructor
    @EqualsAndHashCode
    @ToString
    public static class Identifier {
        private final String occupation;
        private final String companyName;
    }

    enum Status {
        NEW, RENEW, END;
    }
}
