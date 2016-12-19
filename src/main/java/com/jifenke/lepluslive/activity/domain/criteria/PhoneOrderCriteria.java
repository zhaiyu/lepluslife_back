package com.jifenke.lepluslive.activity.domain.criteria;

/**
 * 话费订单筛选条件 Created by zhangwen on 16/10/27.
 */
public class PhoneOrderCriteria {

  private Integer state; //订单状态

  //下单时间区域
  private String startDate;
  private String endDate;

  private String phone; //充值手机号

  private String userSid; //充值会员SID

  private Integer worth; //话费金额

  private Long ruleId; //话费产品ID

  private String orderId;  //第三方充值平台的订单编号

  private Integer currPage = 1;  //当前页码

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

  public void setEndDate(String endDate) {
    this.endDate = endDate;
  }

  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  public String getOrderId() {
    return orderId;
  }

  public void setOrderId(String orderId) {
    this.orderId = orderId;
  }

  public String getUserSid() {
    return userSid;
  }

  public void setUserSid(String userSid) {
    this.userSid = userSid;
  }

  public Integer getWorth() {
    return worth;
  }

  public void setWorth(Integer worth) {
    this.worth = worth;
  }

  public Integer getCurrPage() {
    return currPage;
  }

  public void setCurrPage(Integer currPage) {
    this.currPage = currPage;
  }

  public Long getRuleId() {
    return ruleId;
  }

  public void setRuleId(Long ruleId) {
    this.ruleId = ruleId;
  }
}
