package org.kimtaehoondev.jobpostingcollector.email.service;

import java.util.List;
import org.kimtaehoondev.jobpostingcollector.jobposting.JobPosting;

public interface EmailService {
    void sendJobPostingUpdateToAll(List<JobPosting> jobPostings);
}
