package com.jifenke.lepluslive.printer.domain.criteria;

/**
 * Created by lss on 2016/12/29.
 */
public class ReceiptCriteria {
    private Integer offset;

    private Long id;

    private String offlineOrderSid;

    private String machineCode;

    private Integer state;

    private String receiptSid;

    private String startDate;

    private String endDate;


    private String merchantName;

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    public Integer getOffset() {
        return offset;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOfflineOrderSid() {
        return offlineOrderSid;
    }

    public void setOfflineOrderSid(String offlineOrderSid) {
        this.offlineOrderSid = offlineOrderSid;
    }

    public String getMachineCode() {
        return machineCode;
    }

    public void setMachineCode(String machineCode) {
        this.machineCode = machineCode;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getReceiptSid() {
        return receiptSid;
    }

    public void setReceiptSid(String receiptSid) {
        this.receiptSid = receiptSid;
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
}
