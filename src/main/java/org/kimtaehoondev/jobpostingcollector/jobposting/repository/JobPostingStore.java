package org.kimtaehoondev.jobpostingcollector.jobposting.repository;

import java.util.List;
import org.kimtaehoondev.jobpostingcollector.jobposting.JobPosting;

public interface JobPostingStore {
    void updateAll(List<JobPosting> result);
}
