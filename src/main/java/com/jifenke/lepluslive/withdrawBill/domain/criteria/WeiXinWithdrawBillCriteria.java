package com.jifenke.lepluslive.withdrawBill.domain.criteria;

/**
 * Created by lss on 2017/5/15.
 */
public class WeiXinWithdrawBillCriteria {
    private Integer offset;

    private String startDate;

    private String endDate;

    private Integer state;

    private String partnerName;

    private String partnerSid;

    public Integer getOffset() {
        return offset;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }

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

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getPartnerName() {
        return partnerName;
    }

    public void setPartnerName(String partnerName) {
        this.partnerName = partnerName;
    }

    public String getPartnerSid() {
        return partnerSid;
    }

    public void setPartnerSid(String partnerSid) {
        this.partnerSid = partnerSid;
    }
}
