package org.kimtaehoondev.jpcollector.jobposting;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class JobPostingDataResolverIntegrationTest {
    @Autowired
    JobPostingResolver jobPostingResolver;

    @Test
    void test() {
        jobPostingResolver.crawling();
    }

}