package com.jifenke.lepluslive.yibao.domain.criteria;

/**
 * 转账记录 - 查询条件
 * Created by xf on 17-7-14.
 */
public class LedgerTransferCriteria {

  private Integer offset;        // 页数
  private String orderSid;       // 转账单号
  private String ledgerNo;       // 易宝子商户号
  private Integer state;          // 转账状态 0=待转账，1=转账成功，其他为易宝错误码

  public Integer getOffset() {
    return offset;
  }

  public void setOffset(Integer offset) {
    this.offset = offset;
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

  public Integer getState() {
    return state;
  }

  public void setState(Integer state) {
    this.state = state;
  }
}
