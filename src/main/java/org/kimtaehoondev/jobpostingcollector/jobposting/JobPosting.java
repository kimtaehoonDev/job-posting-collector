package org.kimtaehoondev.jobpostingcollector.jobposting;

import java.net.URL;
import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
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

    public static JobPosting create(String occupation, String companyName, URL link, String infos) {
        return new JobPosting(null, occupation, companyName,
            link, infos, Status.NEW, LocalDateTime.now());
    }

    public void renew() {
        status = Status.RENEW;
    }

    public enum Status {
        NEW, RENEW, END;
    }
}
