package com.jifenke.lepluslive.yibao.domain.entities;

import com.jifenke.lepluslive.global.util.MvUtil;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 易宝转账记录
 * Created by zhangwen on 2017/7/12.
 */
@Entity
@Table(name = "YB_LEDGER_TRANSFER")
public class LedgerTransfer {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  private Date dateCreated = new Date();

  /**
   * 如果一次转账失败，则需要系统自动生成一笔转账单，发起第二次重试。
   * 第二次重试再失败的话，无需发起重试，但需要给相关业务人员发送一条提醒短信。
   */
  private Integer state = 0;   //转账状态 0=待转账，1=转账成功，其他为易宝错误码

  @Column(nullable = false, length = 30)
  private String orderSid = MvUtil.getOrderNumber(7); //转账单号(唯一)

  @Column(nullable = false, length = 10)
  private String tradeDate; //清算日期（对应的交易记录是哪一天完成的）（yyyy-MM-dd）

  @Column(nullable = false, length = 50)
  private String ledgerNo;  //易宝的子商户号

  private Long actualTransfer = 0L;   //应转账金额

  private Date dateCompleted;  //转账成功的时间

  private Integer type = 1;  //转账类型 结算转账	目前只有这一种，预留该字段为以后拓展

  @Column(nullable = false, length = 30)
  private String ledgerSid;  //通道结算单号=LedgerSettlement.orderSid

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Date getDateCreated() {
    return dateCreated;
  }

  public void setDateCreated(Date dateCreated) {
    this.dateCreated = dateCreated;
  }

  public Integer getState() {
    return state;
  }

  public void setState(Integer state) {
    this.state = state;
  }

  public String getOrderSid() {
    return orderSid;
  }

  public void setOrderSid(String orderSid) {
    this.orderSid = orderSid;
  }

  public String getTradeDate() {
    return tradeDate;
  }

  public void setTradeDate(String tradeDate) {
    this.tradeDate = tradeDate;
  }

  public String getLedgerNo() {
    return ledgerNo;
  }

  public void setLedgerNo(String ledgerNo) {
    this.ledgerNo = ledgerNo;
  }

  public Long getActualTransfer() {
    return actualTransfer;
  }

  public void setActualTransfer(Long actualTransfer) {
    this.actualTransfer = actualTransfer;
  }

  public Date getDateCompleted() {
    return dateCompleted;
  }

  public void setDateCompleted(Date dateCompleted) {
    this.dateCompleted = dateCompleted;
  }

  public Integer getType() {
    return type;
  }

  public void setType(Integer type) {
    this.type = type;
  }

  public String getLedgerSid() {
    return ledgerSid;
  }

  public void setLedgerSid(String ledgerSid) {
    this.ledgerSid = ledgerSid;
  }
}
