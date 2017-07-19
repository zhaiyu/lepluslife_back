package com.jifenke.lepluslive.order.domain.criteria;

/**
 * 富友扫码订单查询条件 Created by zhangwen on 16/12/19.
 */
public class ScanCodeOrderCriteria {

  private String startDate;

  private String endDate;

  private Integer payment;  ////付款方式  0=纯现金|1=纯红包|2=混合

  private String userSid; //消费者TOKEN

  private String phoneNumber; //消费者手机号

  private String merchantName;  //门店名称=Merchant.name

  private Long merchantUserId;  //商户ID=MerchantUser.id

  private Integer orderType; //订单类型6种=Category.id(12001~12006)

  private String orderSid;  //订单编号

  private String merchantNum; //移动商户号

  private Integer state;//支付状态  0=未支付|1=已支付|2=已退款

  private Integer source;  //支付来源  0=WAP|1=APP

  private Integer offset; //页码

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

  public Integer getPayment() {
    return payment;
  }

  public void setPayment(Integer payment) {
    this.payment = payment;
  }

  public String getUserSid() {
    return userSid;
  }

  public void setUserSid(String userSid) {
    this.userSid = userSid;
  }

  public String getPhoneNumber() {
    return phoneNumber;
  }

  public void setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
  }

  public String getMerchantName() {
    return merchantName;
  }

  public void setMerchantName(String merchantName) {
    this.merchantName = merchantName;
  }

  public Long getMerchantUserId() {
    return merchantUserId;
  }

  public void setMerchantUserId(Long merchantUserId) {
    this.merchantUserId = merchantUserId;
  }

  public Integer getSource() {
    return source;
  }

  public void setSource(Integer source) {
    this.source = source;
  }

  public Integer getOrderType() {
    return orderType;
  }

  public void setOrderType(Integer orderType) {
    this.orderType = orderType;
  }

  public String getOrderSid() {
    return orderSid;
  }

  public void setOrderSid(String orderSid) {
    this.orderSid = orderSid;
  }

  public String getMerchantNum() {
    return merchantNum;
  }

  public void setMerchantNum(String merchantNum) {
    this.merchantNum = merchantNum;
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
}
