package org.kimtaehoondev.jobpostingcollector.jobposting.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.kimtaehoondev.jobpostingcollector.jobposting.JobPosting;
import org.springframework.stereotype.Component;

@Component
public class DefaultJobPostingStore implements JobPostingStore {
    private final Map<JobPosting.Identifier, JobPosting> store = new HashMap<>();

    @Override
    public void updateAll(List<JobPosting> jobPostings) {
        for (JobPosting jobPosting : store.values()) {
            jobPosting.makeEndData();
        }

        for (JobPosting jobPosting : jobPostings) {
            JobPosting.Identifier identifier = jobPosting.getIdentifier();
            if (store.containsKey(identifier)) {
                jobPosting.makeRenewData();
            }
            store.put(identifier, jobPosting);
        }

        for (Map.Entry<JobPosting.Identifier, JobPosting> entry : store.entrySet()) {
            if (entry.getValue().isEnd()) {
                store.remove(entry.getKey());
            }
        }
    }
}
