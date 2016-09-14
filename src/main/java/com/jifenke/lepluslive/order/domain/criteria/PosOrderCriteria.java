package com.jifenke.lepluslive.order.domain.criteria;

/**
 * Created by wcg on 16/9/5.
 */
public class PosOrderCriteria {

  private Integer offset;

  private String startDate;

  private String endDate;

  private Long merchantLocation;

  private Long rebateWay;

  private Long tradeFlag;

  private String merchant;

  private String userSid;

  private String userPhone;

  private Integer state;

  public Integer getState() {
    return state;
  }

  public void setState(Integer state) {
    this.state = state;
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

  public Integer getOffset() {
    return offset;
  }

  public void setOffset(Integer offset) {
    this.offset = offset;
  }

  public void setEndDate(String endDate) {
    this.endDate = endDate;
  }

  public Long getMerchantLocation() {
    return merchantLocation;
  }

  public void setMerchantLocation(Long merchantLocation) {
    this.merchantLocation = merchantLocation;
  }

  public Long getRebateWay() {
    return rebateWay;
  }

  public void setRebateWay(Long rebateWay) {
    this.rebateWay = rebateWay;
  }

  public Long getTradeFlag() {
    return tradeFlag;
  }

  public void setTradeFlag(Long tradeFlag) {
    this.tradeFlag = tradeFlag;
  }

  public String getMerchant() {
    return merchant;
  }

  public void setMerchant(String merchant) {
    this.merchant = merchant;
  }

  public String getUserSid() {
    return userSid;
  }

  public void setUserSid(String userSid) {
    this.userSid = userSid;
  }

  public String getUserPhone() {
    return userPhone;
  }

  public void setUserPhone(String userPhone) {
    this.userPhone = userPhone;
  }
}
