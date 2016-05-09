package com.jifenke.lepluslive.order.domain.criteria;

/**
 * Created by wcg on 16/5/9.
 */
public class FinancialCriteria {

  private String startDate;

  private String endDate;

  private String merchant;

  private Integer offset;

  private Integer state;

  public Integer getState() {
    return state;
  }

  public void setState(Integer state) {
    this.state = state;
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

  public String getEndDate() {
    return endDate;
  }

  public void setEndDate(String endDate) {
    this.endDate = endDate;
  }

  public String getMerchant() {
    return merchant;
  }

  public void setMerchant(String merchant) {
    this.merchant = merchant;
  }
}
