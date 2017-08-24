package com.jifenke.lepluslive.groupon.domain.criteria;

/**
 * 团购列表查询条件
 * Created by zhangwen on 17/8/23
 */
public class GrouponStatisticCriteria {

  private String startDate;

  private String endDate;

  private String transferStartDate;

  private String transferEndDate;

  private String merchant;

  private Integer offset;

  private Integer state;

  public String getTransferStartDate() {
    return transferStartDate;
  }

  public void setTransferStartDate(String transferStartDate) {
    this.transferStartDate = transferStartDate;
  }

  public String getTransferEndDate() {
    return transferEndDate;
  }

  public void setTransferEndDate(String transferEndDate) {
    this.transferEndDate = transferEndDate;
  }

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
