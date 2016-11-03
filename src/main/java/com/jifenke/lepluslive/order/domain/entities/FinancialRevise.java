package com.jifenke.lepluslive.order.domain.entities;

import com.jifenke.lepluslive.global.util.MvUtil;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * Created by wcg on 2016/11/1.
 * 结算单修正
 */
@Entity
@Table(name = "FINANCIAL_REVISE")
public class FinancialRevise {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @OneToOne
  private FinancialStatistic financialStatistic;

  private Long revisePosTransfer;

  private Long revisePosTransTruePay;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public FinancialStatistic getFinancialStatistic() {
    return financialStatistic;
  }

  public void setFinancialStatistic(FinancialStatistic financialStatistic) {
    this.financialStatistic = financialStatistic;
  }

  public Long getRevisePosTransfer() {
    return revisePosTransfer;
  }

  public void setRevisePosTransfer(Long revisePosTransfer) {
    this.revisePosTransfer = revisePosTransfer;
  }

  public Long getRevisePosTransTruePay() {
    return revisePosTransTruePay;
  }

  public void setRevisePosTransTruePay(Long revisePosTransTruePay) {
    this.revisePosTransTruePay = revisePosTransTruePay;
  }
}
