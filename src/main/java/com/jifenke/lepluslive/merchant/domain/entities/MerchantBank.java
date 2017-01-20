package com.jifenke.lepluslive.merchant.domain.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by wcg on 16/5/5.
 */
@Entity
@Table(name = "MERCHANT_BANK")
public class MerchantBank {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  private String bankNumber; //结算卡号或账户

  private String bankName; //开户支行

  private Integer type = 0;  //账户类型  0=法人私账|1=非法人私账|2=对公账号

  @Column(length = 100)
  private String payee;    //收款人或账户主体

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getBankNumber() {
    return bankNumber;
  }

  public void setBankNumber(String bankNumber) {
    this.bankNumber = bankNumber;
  }

  public String getBankName() {
    return bankName;
  }

  public void setBankName(String bankName) {
    this.bankName = bankName;
  }

  public Integer getType() {
    return type;
  }

  public void setType(Integer type) {
    this.type = type;
  }

  public String getPayee() {
    return payee;
  }

  public void setPayee(String payee) {
    this.payee = payee;
  }
}
