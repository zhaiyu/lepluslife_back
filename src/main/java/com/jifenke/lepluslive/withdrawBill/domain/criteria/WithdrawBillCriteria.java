package com.jifenke.lepluslive.withdrawBill.domain.criteria;

/**
 * Created by lss on 2016/8/26.
 */
public class WithdrawBillCriteria {

  private Integer offset;

  private Integer billType;

  private Integer state;

  private String merchant;

  private String partner;

  private String partnerManager;

  private String startDate;

  private String endDate;

  private Integer amount;

  private String withdrawBillSid;

  public Integer getOffset() {
    return offset;
  }

  public void setOffset(Integer offset) {
    this.offset = offset;
  }

  public Integer getState() {
    return state;
  }

  public void setState(Integer state) {
    this.state = state;
  }


  public String getEndDate() {
    return endDate;
  }

  public void setEndDate(String endDate) {
    this.endDate = endDate;
  }

  public String getStartDate() {
    return startDate;
  }

  public void setStartDate(String startDate) {
    this.startDate = startDate;
  }

  public Integer getBillType() {
    return billType;
  }

  public void setBillType(Integer billType) {
    this.billType = billType;
  }

  public Integer getAmount() {
    return amount;
  }

  public void setAmount(Integer amount) {
    this.amount = amount;
  }

  public String getWithdrawBillSid() {
    return withdrawBillSid;
  }

  public String getPartnerManager() {
    return partnerManager;
  }

  public void setPartnerManager(String partnerManager) {
    this.partnerManager = partnerManager;
  }

  public String getPartner() {

    return partner;
  }

  public void setPartner(String partner) {
    this.partner = partner;
  }

  public String getMerchant() {

    return merchant;
  }

  public void setMerchant(String merchant) {
    this.merchant = merchant;
  }

  public void setWithdrawBillSid(String withdrawBillSid) {
    this.withdrawBillSid = withdrawBillSid;
  }

}
