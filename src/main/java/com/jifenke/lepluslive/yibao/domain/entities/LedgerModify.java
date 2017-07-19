package com.jifenke.lepluslive.yibao.domain.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * 易宝子商户修改记录
 * Created by zhangwen on 2017/7/15.
 */
@Entity
@Table(name = "YB_LEDGER_MODIFY")
public class LedgerModify {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  private Date dateCreated = new Date();

  private Date dateCompleted;  //异步通知时间

  @Column(nullable = false, unique = true, length = 30)
  private String requestId; //修改请求号(唯一)

  @ManyToOne
  @NotNull
  private MerchantUserLedger merchantUserLedger;

  private Integer state = 0;   //修改状态 0=审核中，1=成功，2=失败

  /**
   * dataAfter&dataBefore字段顺序如下：
   * 绑定手机号,银行卡号,开户行,开户省,开户市,起结金额
   */
  private String dataAfter;   //修改后信息集合，以‘,’分隔

  private String dataBefore;   //修改前信息集合，以‘,’分隔,失败将根据此字段回滚子商户信息

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

  public String getRequestId() {
    return requestId;
  }

  public void setRequestId(String requestId) {
    this.requestId = requestId;
  }

  public MerchantUserLedger getMerchantUserLedger() {
    return merchantUserLedger;
  }

  public void setMerchantUserLedger(
      MerchantUserLedger merchantUserLedger) {
    this.merchantUserLedger = merchantUserLedger;
  }

  public Date getDateCompleted() {
    return dateCompleted;
  }

  public void setDateCompleted(Date dateCompleted) {
    this.dateCompleted = dateCompleted;
  }


  public String getDataAfter() {
    return dataAfter;
  }

  public void setDataAfter(String dataAfter) {
    this.dataAfter = dataAfter;
  }

  public String getDataBefore() {
    return dataBefore;
  }

  public void setDataBefore(String dataBefore) {
    this.dataBefore = dataBefore;
  }
}
