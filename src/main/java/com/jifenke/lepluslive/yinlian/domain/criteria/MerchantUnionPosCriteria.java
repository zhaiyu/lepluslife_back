package com.jifenke.lepluslive.yinlian.domain.criteria;

/**
 * Created by sunxingfei on 2017/4/11.
 */
public class MerchantUnionPosCriteria {
    private String merchantSid;
    private String commission;
    private String scoreARebate;
    private String scoreBRebate;
    private String userScoreARebate;
    private String userScoreBRebate;
    private String userGeneralACommission;
    private Boolean useCommission;
    private Long merchantUnionPosId;
    private Integer isNonCardCommission;

    public Integer getIsNonCardCommission() {
        return isNonCardCommission;
    }

    public void setIsNonCardCommission(Integer isNonCardCommission) {
        this.isNonCardCommission = isNonCardCommission;
    }

    public String getMerchantSid() {
        return merchantSid;
    }

    public void setMerchantSid(String merchantSid) {
        this.merchantSid = merchantSid;
    }

    public String getCommission() {
        return commission;
    }

    public void setCommission(String commission) {
        this.commission = commission;
    }

    public String getScoreARebate() {
        return scoreARebate;
    }

    public void setScoreARebate(String scoreARebate) {
        this.scoreARebate = scoreARebate;
    }

    public String getScoreBRebate() {
        return scoreBRebate;
    }

    public void setScoreBRebate(String scoreBRebate) {
        this.scoreBRebate = scoreBRebate;
    }

    public String getUserScoreARebate() {
        return userScoreARebate;
    }

    public void setUserScoreARebate(String userScoreARebate) {
        this.userScoreARebate = userScoreARebate;
    }

    public String getUserScoreBRebate() {
        return userScoreBRebate;
    }

    public void setUserScoreBRebate(String userScoreBRebate) {
        this.userScoreBRebate = userScoreBRebate;
    }

    public String getUserGeneralACommission() {
        return userGeneralACommission;
    }

    public void setUserGeneralACommission(String userGeneralACommission) {
        this.userGeneralACommission = userGeneralACommission;
    }

    public Boolean getUseCommission() {
        return useCommission;
    }

    public void setUseCommission(Boolean useCommission) {
        this.useCommission = useCommission;
    }

    public Long getMerchantUnionPosId() {
        return merchantUnionPosId;
    }

    public void setMerchantUnionPosId(Long merchantUnionPosId) {
        this.merchantUnionPosId = merchantUnionPosId;
    }
}
