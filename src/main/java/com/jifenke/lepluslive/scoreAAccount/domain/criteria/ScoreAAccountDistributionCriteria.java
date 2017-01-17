package com.jifenke.lepluslive.scoreAAccount.domain.criteria;

/**
 * Created by lss on 2016/12/7.
 */
public class ScoreAAccountDistributionCriteria {
    private String startDate;

    private String endDate;

    private Integer offset;

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public Integer getOffset() {
        return offset;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }
}
