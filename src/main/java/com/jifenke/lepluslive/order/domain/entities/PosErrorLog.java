package com.jifenke.lepluslive.order.domain.entities;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Created by wcg on 2016/10/31.
 */
@Entity
@Table(name = "POS_ERROR_LOG")
public class PosErrorLog {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  private Date createDate;

  private Long localTruePay; //本地记录实际支付

  private Long localCommission; //本地实际手续费

  private Long remoteTruePay;//excel记录的实际支付

  private Long remoteCommission; //excel记录的实际手续费

  private Integer localState; //本地支付状态  0未支付 1 已支付 2不存在

  private Integer remoteState = 1;//excel记录的支付状态

  private String orderSid;

  @ManyToOne
  private PosDailyBill posDailyBill;

  public PosDailyBill getPosDailyBill() {
    return posDailyBill;
  }

  public void setPosDailyBill(PosDailyBill posDailyBill) {
    this.posDailyBill = posDailyBill;
  }

  public String getOrderSid() {
    return orderSid;
  }

  public void setOrderSid(String orderSid) {
    this.orderSid = orderSid;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Date getCreateDate() {
    return createDate;
  }

  public void setCreateDate(Date createDate) {
    this.createDate = createDate;
  }

  public Long getLocalTruePay() {
    return localTruePay;
  }

  public void setLocalTruePay(Long localTruePay) {
    this.localTruePay = localTruePay;
  }

  public Long getLocalCommission() {
    return localCommission;
  }

  public void setLocalCommission(Long localCommission) {
    this.localCommission = localCommission;
  }

  public Long getRemoteTruePay() {
    return remoteTruePay;
  }

  public void setRemoteTruePay(Long remoteTruePay) {
    this.remoteTruePay = remoteTruePay;
  }

  public Long getRemoteCommission() {
    return remoteCommission;
  }

  public void setRemoteCommission(Long remoteCommission) {
    this.remoteCommission = remoteCommission;
  }

  public Integer getLocalState() {
    return localState;
  }

  public void setLocalState(Integer localState) {
    this.localState = localState;
  }

  public Integer getRemoteState() {
    return remoteState;
  }

  public void setRemoteState(Integer remoteState) {
    this.remoteState = remoteState;
  }
}
