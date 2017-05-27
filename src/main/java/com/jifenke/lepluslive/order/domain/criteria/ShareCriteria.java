package com.jifenke.lepluslive.order.domain.criteria;

/**
 * Created by wcg on 16/7/19.
 */
public class ShareCriteria {

  private String startDate;

  private String endDate;

  private String userSid;

  private String userPhone;

  private String bindMerchant;

  private String bindPartner;

  private String tradeMerchant;

  private String tradePartner;

  private String lockPartnerManager;

  private String orderSid;

  private Integer offset;

  private Integer type;  //1=扫码分润   2=pos分润

  public Integer getType() {
    return type;
  }

  public void setType(Integer type) {
    this.type = type;
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

  public String getBindMerchant() {
    return bindMerchant;
  }

  public void setBindMerchant(String bindMerchant) {
    this.bindMerchant = bindMerchant;
  }

  public String getBindPartner() {
    return bindPartner;
  }

  public void setBindPartner(String bindPartner) {
    this.bindPartner = bindPartner;
  }

  public String getTradeMerchant() {
    return tradeMerchant;
  }

  public void setTradeMerchant(String tradeMerchant) {
    this.tradeMerchant = tradeMerchant;
  }

  public String getTradePartner() {
    return tradePartner;
  }

  public void setTradePartner(String tradePartner) {
    this.tradePartner = tradePartner;
  }

  public String getOrderSid() {
    return orderSid;
  }

  public void setOrderSid(String orderSid) {
    this.orderSid = orderSid;
  }

  public String getLockPartnerManager() {
    return lockPartnerManager;
  }

  public void setLockPartnerManager(String lockPartnerManager) {
    this.lockPartnerManager = lockPartnerManager;
  }
}
