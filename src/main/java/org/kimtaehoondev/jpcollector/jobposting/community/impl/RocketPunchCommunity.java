package org.kimtaehoondev.jpcollector.jobposting.community.impl;

import java.util.List;
import org.kimtaehoondev.jpcollector.jobposting.community.JobPostingCommunity;
import org.kimtaehoondev.jpcollector.jobposting.community.JobPostingCommunityType;
import org.springframework.stereotype.Component;

@Component
public class RocketPunchCommunity implements JobPostingCommunity {
    private Status status = Status.GOOD;

    @Override
    public JobPostingCommunityType getCommunityType() {
        return JobPostingCommunityType.ROCKET_PUNCH;
    }

    @Override
    public void changeStatus(Status status) {
        this.status = status;
    }

    @Override
    public boolean isConnected() {
        // TODO
        return true;
    }

    @Override
    public boolean isStatusBad() {
        return Status.BAD == status;
    }

    @Override
    public String getJobPostingElementCssSelector() {
        return "#search-results .company-list .company";
    }

    @Override
    public String getLinkCssSelector() {
        return ".company-jobs-detail .job-name a";
    }

    @Override
    public String getOccupationCssSelector() {
        return ".company-jobs-detail .job-name";
    }

    @Override
    public String getCompanyCssSelector() {
        return ".company-name a";
    }

    @Override
    public List<String> getInfoListCssSelector() {
        return List.of(".job-badges .reward-banner span", ".job-badges .label div");
    }

}
