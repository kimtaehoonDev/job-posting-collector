package org.kimtaehoondev.jpcollector.jobposting.community.impl;

import java.util.List;
import org.kimtaehoondev.jpcollector.jobposting.community.JobPostingCommunity;
import org.kimtaehoondev.jpcollector.jobposting.community.JobPostingCommunityType;
import org.springframework.stereotype.Component;

@Component
public class JumpitCommunity implements JobPostingCommunity {
    private JobPostingCommunityType communityType = JobPostingCommunityType.JUMPIT;
    private Status status = Status.GOOD;

    @Override
    public JobPostingCommunityType getCommunityType() {
        return communityType;
    }

    @Override
    public void changeStatus(Status status) {
        this.status = status;
    }

    @Override
    public String getJobPostingElementCssSelector() {
        return ".sc-gHjyzD .sc-bQtKYq";
    }

    @Override
    public String getLinkCssSelector() {
        return "a";
    }

    @Override
    public String getOccupationCssSelector() {
        return ".sc-dVNjXY .position_card_info_title";
    }

    @Override
    public String getCompanyCssSelector() {
        return ".sc-dVNjXY .sc-xiLah";
    }

    @Override
    public List<String> getInfoListCssSelector() {
        return List.of(".sc-jHkVzv li");
    }

    @Override
    public boolean isStatusBad() {
        return false;
    }
}
