package org.kimtaehoondev.jobpostingcollector.jobposting;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class JobPosting {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    private Long id;

    @Getter
    private String occupation;

    @Getter
    private String companyName;

    private URL link;

    @Getter
    private String infos;

    private Status status;

    private LocalDateTime createdAt;


    @Builder
    private JobPosting(String occupation, String companyName, URL link, List<String> infos) {
        this.occupation = occupation;
        this.companyName = companyName;
        this.link = link;
        this.infos = infos.stream().map(String::toLowerCase).collect(Collectors.joining(" | "));
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
        return infos.contains(info.toLowerCase());
    }

    enum Status {
        NEW, RENEW, END;
    }
}
