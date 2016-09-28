package com.jifenke.lepluslive.merchant.domain.criteria;

/**
 * Created by wcg on 16/5/10.
 */
public class MerchantCriteria {

  private Integer partnership;

  private String merchant;

  private Integer offset;

  private Long merchantType;

  private Long city; //所属城镇

  private Integer storeState; //乐店状态

  private Integer receiptAuth;

  private String startDate;

  private String endDate;

  private String merchantName;

  private String merchantSid;

  private Long salesStaff;

  public Long getSalesStaff() {return salesStaff;}

  public void setSalesStaff(Long salesStaff) {this.salesStaff = salesStaff;}

  public Long getCity() {
    return city;
  }

  public void setCity(Long city) {
    this.city = city;
  }

  public Integer getStoreState() {
    return storeState;
  }

  public void setStoreState(Integer storeState) {
    this.storeState = storeState;
  }

  public Integer getReceiptAuth() {
    return receiptAuth;
  }

  public void setReceiptAuth(Integer receiptAuth) {
    this.receiptAuth = receiptAuth;
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

  public Long getMerchantType() {
    return merchantType;
  }

  public void setMerchantType(Long merchantType) {
    this.merchantType = merchantType;
  }

  public Integer getPartnership() {
    return partnership;
  }

  public void setPartnership(Integer partnership) {
    this.partnership = partnership;
  }

  public String getMerchant() {
    return merchant;
  }

  public void setMerchant(String merchant) {
    this.merchant = merchant;
  }

  public Integer getOffset() {
    return offset;
  }

  public String getMerchantSid() {return merchantSid;}

  public void setMerchantSid(String merchantSid) {this.merchantSid = merchantSid;}

  public String getMerchantName() {return merchantName;}

  public void setMerchantName(String merchantName) {this.merchantName = merchantName;}

  public void setOffset(Integer offset) {
    this.offset = offset;
  }
}
