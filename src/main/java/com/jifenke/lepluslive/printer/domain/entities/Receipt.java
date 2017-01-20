package com.jifenke.lepluslive.printer.domain.entities;

import com.jifenke.lepluslive.order.domain.entities.OffLineOrder;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by lss on 2016/12/27.
 */
@Entity
@Table(name = "Receipt")
public class Receipt  {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String receiptSid;

    private Integer state; //1 成功 0失败 3补打成功 4补打失败

    private Date completeDate;

    @ManyToOne
    private OffLineOrder offLineOrder;

    @ManyToOne
    private Printer printer;

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

    public OffLineOrder getOffLineOrder() {
        return offLineOrder;
    }

    public void setOffLineOrder(OffLineOrder offLineOrder) {
        this.offLineOrder = offLineOrder;
    }

    public Printer getPrinter() {
        return printer;
    }

    public void setPrinter(Printer printer) {
        this.printer = printer;
    }

}

