package com.jifenke.lepluslive.scoreAAccount.domain.criteria;

/**
 * Created by lss on 2016/9/14.
 */
public class ScoreAAccountCriteria {
  private String startDate;

  private String endDate;

  private Integer useScoreA;

  private Integer settlementAmount;

  private Integer IssuedScoreA;

  private Integer commissionIncome;

  private Integer offset;

  public Integer getOffset() {
    return offset;
  }

  public void setOffset(Integer offset) {
    this.offset = offset;
  }

  public Integer getCommissionIncome() {
    return commissionIncome;
  }

  public void setCommissionIncome(Integer commissionIncome) {
    this.commissionIncome = commissionIncome;
  }

  public String getEndDate() {
    return endDate;
  }

  public void setEndDate(String endDate) {
    this.endDate = endDate;
  }

  public Integer getIssuedScoreA() {
    return IssuedScoreA;
  }

  public void setIssuedScoreA(Integer issuedScoreA) {
    IssuedScoreA = issuedScoreA;
  }

  public Integer getJfkShare() {
    return jfkShare;
  }

  public void setJfkShare(Integer jfkShare) {
    this.jfkShare = jfkShare;
  }

  public Integer getSettlementAmount() {
    return settlementAmount;
  }

  public void setSettlementAmount(Integer settlementAmount) {
    this.settlementAmount = settlementAmount;
  }

  public String getStartDate() {
    return startDate;
  }

  public void setStartDate(String startDate) {
    this.startDate = startDate;
  }

  public Integer getUseScoreA() {
    return useScoreA;
  }

  public void setUseScoreA(Integer useScoreA) {
    this.useScoreA = useScoreA;
  }

  private Integer jfkShare;


}
