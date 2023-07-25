package org.kimtaehoondev.jobpostingcollector.jobposting;

import java.net.URL;
import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.kimtaehoondev.jobpostingcollector.jobposting.community.JobPostingCommunityType;

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

    @Getter
    private URL link;

    @Getter
    private String infos;

    @Getter
    @Enumerated(value = EnumType.STRING)
    private Status status;

    private LocalDateTime createdAt;

    @Enumerated(value = EnumType.STRING)
    private JobPostingCommunityType community;

    public static JobPosting create(String occupation, String companyName, URL link, String infos,
                                    JobPostingCommunityType communityType) {
        return new JobPosting(null, occupation, companyName, link, infos, Status.NEW,
            LocalDateTime.now(), communityType);
    }

    public void renew() {
        status = Status.RENEW;
    }

    public enum Status {
        NEW, RENEW;
    }

}
