package com.jifenke.lepluslive.datacenter.domain.criteria;

/**
 * Created by lss on 2016/9/30.
 */
public class TransactionAnalysisCriteria {

  private Integer offset;

  private String startDate;

  private String endDate;

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

  public String getStartDate() {
    return startDate;
  }

  public void setStartDate(String startDate) {
    this.startDate = startDate;
  }
}
