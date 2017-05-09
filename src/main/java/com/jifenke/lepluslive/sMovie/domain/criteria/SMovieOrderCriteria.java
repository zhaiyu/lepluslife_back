package com.jifenke.lepluslive.sMovie.domain.criteria;

/**
 * Created by root on 17-5-2.
 */
public class SMovieOrderCriteria {

    private Integer offset;

    private String createdStartDate;

    private String createdEndDate;

    private String completedStartDate;

    private String completedEndDate;

    private String dateUsedStartDate;

    private String dateUsedEndDate;

    private String orderSid;

    private String phoneNumber;

    private String userSid;

    private Integer state;

    private String sMovieTerminalId;

    public Integer getOffset() {
        return offset;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    public String getCreatedStartDate() {
        return createdStartDate;
    }

    public void setCreatedStartDate(String createdStartDate) {
        this.createdStartDate = createdStartDate;
    }

    public String getCreatedEndDate() {
        return createdEndDate;
    }

    public void setCreatedEndDate(String createdEndDate) {
        this.createdEndDate = createdEndDate;
    }

    public String getCompletedStartDate() {
        return completedStartDate;
    }

    public void setCompletedStartDate(String completedStartDate) {
        this.completedStartDate = completedStartDate;
    }

    public String getCompletedEndDate() {
        return completedEndDate;
    }

    public void setCompletedEndDate(String completedEndDate) {
        this.completedEndDate = completedEndDate;
    }

    public String getDateUsedStartDate() {
        return dateUsedStartDate;
    }

    public void setDateUsedStartDate(String dateUsedStartDate) {
        this.dateUsedStartDate = dateUsedStartDate;
    }

    public String getDateUsedEndDate() {
        return dateUsedEndDate;
    }

    public void setDateUsedEndDate(String dateUsedEndDate) {
        this.dateUsedEndDate = dateUsedEndDate;
    }

    public String getOrderSid() {
        return orderSid;
    }

    public void setOrderSid(String orderSid) {
        this.orderSid = orderSid;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getUserSid() {
        return userSid;
    }

    public void setUserSid(String userSid) {
        this.userSid = userSid;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getsMovieTerminalId() {
        return sMovieTerminalId;
    }

    public void setsMovieTerminalId(String sMovieTerminalId) {
        this.sMovieTerminalId = sMovieTerminalId;
    }
}
