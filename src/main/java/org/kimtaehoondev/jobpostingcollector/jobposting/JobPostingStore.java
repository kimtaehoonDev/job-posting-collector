package org.kimtaehoondev.jobpostingcollector.jobposting;

import java.util.List;

public interface JobPostingStore {
    void updateAll(List<JobPosting> result);
}
