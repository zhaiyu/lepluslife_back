package com.jifenke.lepluslive.printer.domain.entities;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Created by lss on 2016/12/27.
 */
@Entity
@Table(name = "Receipt")
public class Receipt {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  private String receiptSid;

  private Integer state = 0; //1 成功 0失败 3补打成功 4补打失败

  private Date completeDate;

  @ManyToOne
  private Printer printer;

  private String orderSid;

  private Integer type = 1; //1=乐加|2=通道

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getReceiptSid() {
    return receiptSid;
  }

  public void setReceiptSid(String receiptSid) {
    this.receiptSid = receiptSid;
  }

  public Integer getState() {
    return state;
  }

  public void setState(Integer state) {
    this.state = state;
  }

  public Date getCompleteDate() {
    return completeDate;
  }

  public void setCompleteDate(Date completeDate) {
    this.completeDate = completeDate;
  }

  public Printer getPrinter() {
    return printer;
  }

  public void setPrinter(Printer printer) {
    this.printer = printer;
  }

  public String getOrderSid() {
    return orderSid;
  }

  public void setOrderSid(String orderSid) {
    this.orderSid = orderSid;
  }

  public Integer getType() {
    return type;
  }

  public void setType(Integer type) {
    this.type = type;
  }
}

