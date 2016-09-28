package com.jifenke.lepluslive.order.domain.criteria;

/**
 * 线上订单筛选条件 Created by wcg on 16/4/28.
 */
public class OrderCriteria {

  private String startDate; //订单创建时间

  private String endDate;

  private Integer offset;

  private Integer transmitWay; //取货方式  1=线下自提|2=快递

  private Integer state;//0 未支付 1 已支付 2 已发货 3已收获 4 订单取消

  private String orderSid;

  private String phoneNumber; //收货人手机

  private String userName;  //收货人姓名

  private Long maxTruePrice;   //实付最大金额

  private Long minTruePrice;   //实付最小金额

  public String getOrderSid() {
    return orderSid;
  }

  public void setOrderSid(String orderSid) {
    this.orderSid = orderSid;
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

  public Integer getOffset() {
    return offset;
  }

  public void setOffset(Integer offset) {
    this.offset = offset;
  }

  public Integer getTransmitWay() {
    return transmitWay;
  }

  public String getPhoneNumber() {
    return phoneNumber;
  }

  public void setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
  }

  public Long getMaxTruePrice() {
    return maxTruePrice;
  }

  public void setMaxTruePrice(Long maxTruePrice) {
    this.maxTruePrice = maxTruePrice;
  }

  public Long getMinTruePrice() {
    return minTruePrice;
  }

  public void setMinTruePrice(Long minTruePrice) {
    this.minTruePrice = minTruePrice;
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public void setTransmitWay(Integer transmitWay) {
    this.transmitWay = transmitWay;
  }

  public Integer getState() {
    return state;
  }

  public void setState(Integer state) {
    this.state = state;
  }
}
