package org.kimtaehoondev.jobpostingcollector.jobposting;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class JobPostingResolverIntegrationTest {
    @Autowired
    JobPostingResolver jobPostingResolver;

    @Test
    void test() {
        jobPostingResolver.updateJobPosting();
    }

}