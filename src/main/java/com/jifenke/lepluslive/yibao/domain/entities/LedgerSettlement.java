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
 * 通道结算单
 * Created by zhangwen on 2017/7/12.
 */
@Entity
@Table(name = "YB_LEDGER_SETTLEMENT")
public class LedgerSettlement {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  private Date dateCreated = new Date();

  //【打款成功】【无结算记录】和【已退回】为终态
  //0=待查询，1=打款成功，2=已退回，3=无结算记录，4=已扣款未打款，5=打款中，-1=打款失败，-2=银行返回打款失败
  private Integer state = 0;   //结算状态

  private Integer transferState = 0;  //转账状态 0=待转账，1=转账成功，2=转账失败

  @Column(nullable = false, length = 10)
  private String tradeDate; //结算日期（对应的给商户转账是哪一天完成的）（yyyy-MM-dd）

  @Column(nullable = false, unique = true, length = 30)
  private String orderSid = MvUtil.getOrderNumber(7); //通道结算单号(唯一)

  @Column(nullable = false, length = 50)
  private String ledgerNo;  //易宝的子商户号

  @Column(nullable = false)
  private Long merchantUserId;  //对应的商户ID

  private Long accountBalance = 0L;  //当前子账户余额(转账前查询)

  private Integer settlementCost = 0;   //单笔结算费用

  private Long totalTransfer = 0L;  //日交易转账金额=使用该账户门店结算单之和

  /**
   * 如果结算费用承担方为【积分客承担】
   * 【日交易转账金额+账户余额】小于起结金额时，实际转账金额=日交易转账金额。如果大于等于起结金额，实际转账金额=日交易转账金额+单笔结算费用
   * 如果结算费用承担方为【子商户承担】
   * 实际转账金额=日交易转账金额
   */
  private Long actualTransfer = 0L;   //实际转账金额

  /**
   * 值A=账户余额+实际转账金额。
   * A如果小于起结金额，则通道应结算金额为0.否则通道应结算金额为A
   */
  private Long settlementAmount = 0L;  //通道应结算金额

  private Long settlementTrueAmount = 0L;  //查询后易宝返回的实际结算金额（理应=settlementAmount）

  private Integer costSide = 0;   //结算费用承担方  0=积分客|1=子商户

  private Long minSettleAmount = 1L;  //起结金额 账户余额必须大于等于该值才会结算

  @Column(nullable = false, length = 30)
  private String accountName; //开户名

  @Column(nullable = false, length = 30)
  private String bankAccountNumber;  //出款用的银行卡号【必须为储蓄卡】

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

  public Integer getTransferState() {
    return transferState;
  }

  public void setTransferState(Integer transferState) {
    this.transferState = transferState;
  }

  public String getTradeDate() {
    return tradeDate;
  }

  public void setTradeDate(String tradeDate) {
    this.tradeDate = tradeDate;
  }

  public String getOrderSid() {
    return orderSid;
  }

  public void setOrderSid(String orderSid) {
    this.orderSid = orderSid;
  }

  public String getLedgerNo() {
    return ledgerNo;
  }

  public void setLedgerNo(String ledgerNo) {
    this.ledgerNo = ledgerNo;
  }

  public Long getMerchantUserId() {
    return merchantUserId;
  }

  public void setMerchantUserId(Long merchantUserId) {
    this.merchantUserId = merchantUserId;
  }

  public Long getAccountBalance() {
    return accountBalance;
  }

  public void setAccountBalance(Long accountBalance) {
    this.accountBalance = accountBalance;
  }

  public Integer getSettlementCost() {
    return settlementCost;
  }

  public void setSettlementCost(Integer settlementCost) {
    this.settlementCost = settlementCost;
  }

  public Long getTotalTransfer() {
    return totalTransfer;
  }

  public void setTotalTransfer(Long totalTransfer) {
    this.totalTransfer = totalTransfer;
  }

  public Long getActualTransfer() {
    return actualTransfer;
  }

  public void setActualTransfer(Long actualTransfer) {
    this.actualTransfer = actualTransfer;
  }

  public Long getSettlementAmount() {
    return settlementAmount;
  }

  public void setSettlementAmount(Long settlementAmount) {
    this.settlementAmount = settlementAmount;
  }

  public Long getSettlementTrueAmount() {
    return settlementTrueAmount;
  }

  public void setSettlementTrueAmount(Long settlementTrueAmount) {
    this.settlementTrueAmount = settlementTrueAmount;
  }

  public Integer getCostSide() {
    return costSide;
  }

  public void setCostSide(Integer costSide) {
    this.costSide = costSide;
  }

  public Long getMinSettleAmount() {
    return minSettleAmount;
  }

  public void setMinSettleAmount(Long minSettleAmount) {
    this.minSettleAmount = minSettleAmount;
  }

  public String getAccountName() {
    return accountName;
  }

  public void setAccountName(String accountName) {
    this.accountName = accountName;
  }

  public String getBankAccountNumber() {
    return bankAccountNumber;
  }

  public void setBankAccountNumber(String bankAccountNumber) {
    this.bankAccountNumber = bankAccountNumber;
  }
}
