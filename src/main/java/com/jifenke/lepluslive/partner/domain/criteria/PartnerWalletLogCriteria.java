package com.jifenke.lepluslive.partner.domain.criteria;

/**
 * Created by lss on 2017/5/17.
 */
public class PartnerWalletLogCriteria {
    private Integer offset;

    private Long type;

    private String createdStartDate;

    private String createdEndDate;

    private Long partnerId;

    public Long getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(Long partnerId) {
        this.partnerId = partnerId;
    }

    public Long getType() {
        return type;
    }

    public void setType(Long type) {
        this.type = type;
    }

    public String getCreatedEndDate() {
        return createdEndDate;
    }

    public void setCreatedEndDate(String createdEndDate) {
        this.createdEndDate = createdEndDate;
    }

    public String getCreatedStartDate() {
        return createdStartDate;
    }

    public void setCreatedStartDate(String createdStartDate) {
        this.createdStartDate = createdStartDate;
    }

    public Integer getOffset() {
        return offset;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }

}
